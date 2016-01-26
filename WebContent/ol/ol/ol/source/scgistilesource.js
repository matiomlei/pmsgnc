goog.provide('ol.source.scgisTile');

goog.require('ol.Attribution');
goog.require('ol.TileUrlFunction');
goog.require('ol.source.TileImage');



/**
 * @classdesc
 * Layer source for tile data with URLs in a set scgisTile format.
 *
 * @constructor
 * @extends {ol.source.TileImage}
 * @param {olx.source.XYZOptions} options XYZ options.
 * @api stable
 */
ol.source.scgisTile = function(options) {
  var projection = goog.isDef(options.projection) ?
      options.projection : 'EPSG:4326';
	  // add by yanlin 20150616
	var maxZoom = goog.isDef(options.maxZoom) ?    //定义默认最大级别  为20
      options.maxZoom : 20;
	   var minZoom = goog.isDef(options.minZoom) ?    //定义默认最小级别  为3
      options.minZoom : 3;
	  //add end...
  var tileGrid = ol.tilegrid.createXYZ({
    extent: ol.tilegrid.extentFromProjection(projection),
    maxZoom: options.maxZoom,
    tileSize: options.tileSize
  });

  goog.base(this, {
    attributions: options.attributions,
    crossOrigin: options.crossOrigin,
    logo: options.logo,
    projection: projection,
    tileGrid: tileGrid,
    tileLoadFunction: options.tileLoadFunction,
    tilePixelRatio: options.tilePixelRatio,
    tileUrlFunction: ol.TileUrlFunction.nullTileUrlFunction,
    wrapX: goog.isDef(options.wrapX) ? options.wrapX : true
  });

  /**
   * @private
   * @type {ol.TileCoordTransformType}
   */
  this.tileCoordTransform_ = tileGrid.createTileCoordTransform();
  
   //   add by yanlin     
    if (goog.isDef(options.token)) {
	this.token=options.token;
  }
  if (goog.isDef(options.returnTipTile)) {
	this.returnTipTile=options.returnTipTile;
  }
   if (goog.isDef(options.timeStamp)) {
	this.timeStamp=options.timeStamp;
  }
  if (goog.isDef(options.isZoomToInitialExtent)) {
	this.isZoomToInitialExtent=options.isZoomToInitialExtent;
  }
  if (goog.isDef(options.zoomToExtent)) {
	this.zoomToExtent=options.zoomToExtent;
  }
  
  // add end...

  if (goog.isDef(options.tileUrlFunction)) {
    this.setTileUrlFunction(options.tileUrlFunction);
  } else if (goog.isDef(options.urls)) {
    this.setUrls(options.urls);
  } else if (goog.isDef(options.url)) {
	  //add start
	    var url=options.url+"/tile/{z}/{y}/{x}/";
		url+="?returnTipTile="+this.returnTipTile;
        if (this.token)
            url+="&token="+this.token;
        if (this.timeStamp)
            url+="&timeStamp="+this.timeStamp;
	  //modifiy
		this.setUrl(url);
	
		//add
		$.ajax({
                    type: "get",
                    url: options.url+"/mapserver?f=pjson&token="+this.token,
					dataType: "json",
					async:false,
                    success: function (metaInfo) {
            if (metaInfo){
                if (!metaInfo.success){
                   
                    return;
                }
                else
                {
				
                    ol.source.scgisTile.prototype.serverMetaInfos=metaInfo;
                   ol.source.scgisTile.prototype.maxResolution=metaInfo.message.tileMapCacheInfo.LODInfos[0].resolution;
        
                   ol.source.scgisTile.prototype.zoomOffset=metaInfo.message.tileMapCacheInfo.LODInfos[0].level;
                    var count=metaInfo.message.tileMapCacheInfo.LODInfos.length;
                    ol.source.scgisTile.prototype.resolutions=new Array();
                    for (var i= 0;i<count;i++){
                      ol.source.scgisTile.prototype.resolutions[i]=metaInfo.message.tileMapCacheInfo.LODInfos[i].resolution;
                } 
                   ol.source.scgisTile.prototype.numZoomLevels=count;
                   ol.source.scgisTile.prototype.minResolution=metaInfo.message.tileMapCacheInfo.LODInfos[count-1].resolution;
                 
                   ol.source.scgisTile.prototype.initialExtent=[metaInfo.message.initialExtent.xmin,metaInfo.message.initialExtent.ymin,metaInfo.message.initialExtent.xmax,metaInfo.message.initialExtent.ymax];
                   ol.source.scgisTile.prototype.tileSize=[metaInfo.message.tileMapCacheInfo.tileCols,metaInfo.message.tileMapCacheInfo.tileRows];
                   ol.source.scgisTile.prototype.tileOrigin=[metaInfo.message.tileMapCacheInfo.tileOrigin.x,metaInfo.message.tileMapCacheInfo.tileOrigin.y];
                   ol.source.scgisTile.prototype.tileOriginCorner='tl';
                   ol.source.scgisTile.prototype.maxExtent=[metaInfo.message.fullExtent.xmin,metaInfo.message.fullExtent.ymin,
                        metaInfo.message.fullExtent.xmax,metaInfo.message.fullExtent.ymax];
                    if(ol.source.scgisTile.prototype.map)
                        if (ol.source.scgisTile.prototype.isZoomToInitialExtent)
                      ol.source.scgisTile.prototype.map.zoomToExtent(ol.source.scgisTile.prototype.initialExtent);
						else if (ol.source.scgisTile.prototype.zoomToExtent)
						ol.source.scgisTile.prototype.map.zoomToExtent(ol.source.scgisTile.prototype.zoomToExtent); 

             }
            }
		}
                });
		
  }

};
goog.inherits(ol.source.scgisTile, ol.source.TileImage);


/**
 * @inheritDoc
 * @api
 */
ol.source.scgisTile.prototype.setTileUrlFunction = function(tileUrlFunction) {
  goog.base(this, 'setTileUrlFunction',
      ol.TileUrlFunction.withTileCoordTransform(
          this.tileCoordTransform_, tileUrlFunction));
};


/**
 * Set the URL to use for requests.
 * @param {string} url URL.
 * @api stable
 */
ol.source.scgisTile.prototype.setUrl = function(url) {
  this.setTileUrlFunction(ol.TileUrlFunction.createFromTemplates(
      ol.TileUrlFunction.expandUrl(url)));
};


/**
 * Set the URLs to use for requests.
 * @param {Array.<string>} urls URLs.
 */
ol.source.scgisTile.prototype.setUrls = function(urls) {
  this.setTileUrlFunction(ol.TileUrlFunction.createFromTemplates(urls));
};

ol.source.scgisTile.prototype.token="";   // yanlin add
ol.source.scgisTile.prototype.serverMetaInfos=""; 
ol.source.scgisTile.prototype.maxResolution=""; 
ol.source.scgisTile.prototype.numZoomLevels=""; 
ol.source.scgisTile.prototype.timeStamp;
ol.source.scgisTile.prototype.returnTipTile;
ol.source.scgisTile.prototype.isZoomToInitialExtent;
ol.source.scgisTile.prototype.zoomToExtent; 

