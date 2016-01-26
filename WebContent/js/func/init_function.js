/**
 * 
 */
function init(){
	DwrProcess.init();
	menuButtonClick(0);
}

function menuButtonClick(index) {
	if(index < 2) queryDeployment();
	else if(index == 2) queryQualityDeployment();
    $("#menuButtonList1 li").removeClass("active");
    $("#menuButtonList1 li:eq(index)").addClass("active");
    $divList = $("#funcButtons").children("div");
    $divList.hide();
    $divList.eq(index).show();
    $("#navTabs a:first").tab("show");
}

function cleanRenwuTable(){
	$('#renwuTable').DataTable({
		destroy: true,
		stateSave: true,
		select:{
			style:	'os',
			//selector:'td:first-child'
		},
		data:data,
		order:[[1, 'asc']],
		columns:[
					{
						orderable:false,
						type:'checkbox',
						className:'select-checkbox',
						data:null
					},
		         	{data:'id'},
					{data:'name'},
					{data:'starttime'},
					{data:'endtime'},
					{data:'content'},
					{data:'datapath'},
					{data:'shppath'},
					{data:'iscompleted'}
				]
	});
}

function cleanTaskTable(){
	var taskTable = $('#taskTable').DataTable({
		destroy:true,
		stateSave: true,
		select:{
			style:	'os'
		},
		order:[[1, 'asc']],
		columns:[
					{
						orderable:false,
						type:'checkbox',
						className:'select-checkbox',
						data:null
					},
		         	{data:'objectId'},
					{data:'name'},
					{data:'suoshurenwu'},
					{data:'status'},
					{data:'starttime'},
					{data:'endtime'},
					{data:'worker'},
					{data:'workerPath'},
					{data:'checker'},
					{data:'checkerPath'},
					{data:'inspector'}
				]
	});
}

var projection = ol.proj.get('EPSG:4326');
var domurl="http://www.scgis.net.cn/iMap/iMapServer/DefaultRest/services/sctilemap_dom_dom/";
var dlgurl="http://www.scgis.net.cn/iMap/iMapServer/DefaultRest/services/newtianditudlg/";
var source=new ol.source.scgisTile({
	returnTipTile:true,
	minZoom:3,
	    token:CST.token,
	   projection:"EPSG:4326",
        url: dlgurl
      });
var scgisTileLayer=new ol.layer.Tile({
      source: source});
	
var map = new ol.Map({
  target: 'map',
  layers: [scgisTileLayer],
  view: new ol.View({
    projection: 'EPSG:4326',
    center:[104.07, 30.7],
    zoom: 6
  })
});