/**
 * 
 */
var map;
var vectorLayer;
var selectedFeatures;

var token="-ZwMJYTMrhe8byY9gvdu1GKb6R15Es-MeOhddDpdaiM1WzWz_cUfv3Vp7IK95Dzn";
var projection = ol.proj.get('EPSG:4326');
var dlgurl="http://www.scgis.net.cn/iMap/iMapServer/DefaultRest/services/newtianditudlg/";

var source=new ol.source.scgisTile({
	isZoomToInitialExtent:false,
	zoomToExtent:[103.9,30.6, 104.1,30.8],
	returnTipTile:true,
	minZoom:3,
	token:token,
	projection:"EPSG:4326",
    url: dlgurl
});

var scgisTileLayer=new ol.layer.Tile({source: source});

function initMap(divId){
	$("#"+divId).empty();
	map = new ol.Map({
	    target: divId,
	    layers:[
	            new ol.layer.Tile({source: source})
	    ],
	    view: new ol.View({
		    projection: 'EPSG:4326',
		    center: [103.9,30.6],
		    zoom: 10
	    })
	});
	
	if(divId == 'map'){
		
	}
}

function addVectorLayerToMapByDepId(deploymentId,mapDivId){
	DwrDeployment.getJson(deploymentId,{callback:function(data){getJsonCallback(mapDivId,data)}});
}

function getJsonCallback(mapDivId,data){
	var features = (new ol.format.GeoJSON()).readFeatures(data);
	for(var i=0;i<features.length;i++){
		var feature = features[i];
		var featureId = feature.getId();
		for(var j=0;j<taskData.length;j++){
			if(featureId == taskData[j].objectId){
				feature.setStyle(getStyle(taskData[j].status));
				break;
			}
		}
	}
	var vectorSource = new ol.source.Vector({
		features: features
	});
	vectorLayer = new ol.layer.Vector({
		source: vectorSource
	});
	var extent = vectorSource.getExtent();
	var x = (extent[0] + extent[2]) / 2;
	var y = (extent[1] + extent[3]) / 2;
	if(map.getLayers().array_.length > 1)
		map.removeLayer(map.getLayers().array_[1]);
	map.addLayer(vectorLayer);
	var view = new ol.View({
	    projection: 'EPSG:4326',
	    center: [x,y],
	    zoom: 10
    });
	map.setView(view);
	
	
	var select = new ol.interaction.Select();
	map.addInteraction(select);

	select.on('select', function(e) {
		var rows = $('#taskTable').DataTable().rows();
		var len = rows[0].length;
		for(ind in e.selected){
			for(var i=0;i<len;i++){
		    	if(rows.data()[i].objectId == e.selected[ind].getId()){
		    		if($.inArray(rows[0][i], fentuzuoyeSet) == -1)
		    			fentuzuoyeSet.push(rows[0][i]);
		    		break;
		    	}
		    }
		}
		for(ind in e.deselected){
			for(var i=0;i<len;i++){
		    	if(rows.data()[i].objectId == e.deselected[ind].getId()){
		    		if($.inArray(rows[0][i], fentuzuoyeSet) != -1)
		    			fentuzuoyeSet.splice(fentuzuoyeSet.indexOf(rows[0][i]),1);
		    		break;
		    	}
		    }
		}
		$('#taskTable').DataTable().rows({selected:true}).deselect();
		$('#taskTable').DataTable().rows(fentuzuoyeSet).select();
	});
	
	selectedFeatures = select.getFeatures();
	
	var dragBox = new ol.interaction.DragBox({
		condition: ol.events.condition.shiftKeyOnly,
		style: new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: [0, 0, 255, 1]
	        })
	    })
	});
	map.addInteraction(dragBox);

	//var infoBox = document.getElementById('info');

	dragBox.on('boxend', function(e) {
	  // features that intersect the box are added to the collection of
	  // selected features, and their names are displayed in the "info"
	  // div
		var rows = $('#taskTable').DataTable().rows();
		var len = rows[0].length;
	    var arr = new Array();
	    var info = [];
		var extent = dragBox.getGeometry().getExtent();
		vectorSource.forEachFeatureIntersectingExtent(extent, function(feature) {
			feature.setStyle(getStyle('choose'));
			selectedFeatures.push(feature);
		    for(var i=0;i<len;i++){
		    	if(rows.data()[i].objectId == feature.getId()){
	    		arr.push(rows[0][i]);
	    		if($.inArray(rows[0][i], fentuzuoyeSet) == -1)
	    			fentuzuoyeSet.push(rows[0][i]);
	    		break;
	    	}
	    }
	    info.push(feature.get('name'));
	  });
	  $('#taskTable').DataTable().rows(fentuzuoyeSet).select();
	  if (info.length > 0) {
//	    infoBox.innerHTML = info.join(', ');
	  }
	});

	// clear selection when drawing a new box and when clicking on the map
	dragBox.on('boxstart', function(e) {
	//  selectedFeatures.clear();
//	  infoBox.innerHTML = '&nbsp;';
	});
	
	var popup=new ol.Overlay({
		element:document.getElementById('popup')
	});
	map.addOverlay(popup);
	
	map.on('click',function(evt){
		var element=popup.getElement();
		var coordinate=evt.coordinate;
		var pixel = map.getEventPixel(evt.originalEvent);
		var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
			return feature;
		});
		if(feature){
			$(element).popover('destroy');
			var extent = feature.getGeometry().getExtent();
			var aveX = (extent[0] + extent[2]) /2 ;
			var aveY = (extent[1] + extent[3]) /2 ;
			var useCoord = [aveX,aveY];
			popup.setPosition(useCoord);
			$(element).popover({
				'placement': 'right',
				'animation': false,
				'html': true,
				'content': getPopoverContent(feature.getId())
			});
			$(element).popover('show');
		}
		else{
			$(element).popover('destroy');
		}
	});
}


$('#taskTable').DataTable().on( 'select', function ( e, dt, type, indexes ) {
	if ( type === 'row' ) {
		var num = $('#taskTable').DataTable().rows(indexes)[0][0];
		if($.inArray(num, fentuzuoyeSet) == -1){
			fentuzuoyeSet.push(num);
			var id = $('#taskTable').DataTable().rows(indexes).data()[0].objectId;
			var feature = vectorLayer.getSource().getFeatureById(id);
			selectedFeatures.push(feature);
		}
	}
});


$('#taskTable').DataTable().on( 'deselect', function ( e, dt, type, indexes ) {
	var num = $('#taskTable').DataTable().rows(indexes)[0][0];
	if($.inArray(num, fentuzuoyeSet) != -1){
		fentuzuoyeSet.splice(fentuzuoyeSet.indexOf(num),1);
		var id = $('#taskTable').DataTable().rows(indexes).data()[0].objectId;
		var feature = vectorLayer.getSource().getFeatureById(id);
		selectedFeatures.pop(feature);
	}
});

function getPopoverContent(featureId){
	var res = '<table class="table table-striped table-bordered table-hover" style="white-space:nowrap">';
	for(var i=0;i<taskData.length;i++){
		if(featureId == taskData[i].objectId){
			/*res += '<thead><tr><th>作业编号</th><th>作业内容</th><th>所属任务</th><th>作业状态</th></tr></thead>';
			res += '<tbody><tr><td>' + featureId +'</td><td>' + taskData[i].name + '</td><td>' + taskData[i].suoshurenwu +'</td><td>' + taskData[i].status +'</td></tr></tbody>';
			res += '<thead><tr><th>开始时间</th><th>完成时间</th><th>作图员</th><th>检察员</th><th>质检员</th></tr></thead>';
			res += '<tbody><tr><td>' + taskData[i].starttime +'</td><td>' + taskData[i].endtime +'</td><td>' + taskData[i].worker  +'</td><td>' + taskData[i].checker  +'</td><td>' + taskData[i].inspector +'</td></tr></tbody>';
			res += '<br><a>查看详情</a>';*/
			//res += '<thead><tr><th>作业编号</th><th>作业内容</th><th>所属任务</th><th>作业状态</th></tr></thead>';
			res += '<tbody><tr><td>作业编号</td><td>' + featureId +'</td></tr>'+
					'<tr><td>作业内容</td><td>' + taskData[i].name + '</td></tr>'+
					'<tr><td>所属任务</td><td>' + taskData[i].suoshurenwu +'</td></tr>'+
					'<tr><td>作业状态</td><td>' + taskData[i].status +'</td></tr>'+
					'<tr><td>开始时间</td><td>' + taskData[i].starttime + '</td></tr>'+
					'<tr><td>完成时间</td><td>' + taskData[i].endtime + '</td></tr>'+
					'<tr><td>作图员</td><td>' + taskData[i].worker + '</td></tr>'+
					'<tr><td>检察员</td><td>' + taskData[i].checker + '</td></tr>'+
					'<tr><td>质检员</td><td>' + taskData[i].inspector + '</td></tr></tbody>';
			res += '<br><a>查看详情</a>';
			if(taskData[i].status == '分幅作业未分配') res += '<a>分图作业</a>';
			break;
		}
	}
	res += '</table>';
	return res;
}

function getStyle(type){
	var color;
	switch(type){
	case '分幅作业未分配':
		color = [255,255,255,0.4];
		break;
	case '分幅作业中':
		color = [254,225,136,0.4];
		break;
	case '作业检查未分配':
		color = [160,160,160,0.4];
		break;
	case '作业检查中':
		color = [248,148,6,0.4];
		break;
	case '质量检查未分配':
		color = [58,135,173,0.4];
		break;
	case '质量检查中':
		color = [66,139,202,0.4];
		break;
	case '待提交':
		color = [214,72,126,0.4];
		break;
	case '已提交':
		color = [130,175,111,0.4];
		break; 
	case 'choose':
		color = [250,250,250,0.4];
		break; 
	}
	var styles = [
      new ol.style.Style({
        fill: new ol.style.Fill({
    		color: color
    	}),
    	stroke: new ol.style.Stroke({
    	    color: '#ffcc33',
    	    width: 2
    	})
      })
    ];
	if(type == 'choose'){
		styles = [
		          new ol.style.Style({
		            fill: new ol.style.Fill({
		        		color: [250,250,250,0.4]
		        	}),
		        	stroke: new ol.style.Stroke({
		        	    color: '#ffcc33',
		        	    width: 4
		        	})
		          })
		        ];
	}
	return styles;
}