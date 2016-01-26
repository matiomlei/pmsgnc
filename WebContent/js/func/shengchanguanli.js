/**
 * 
 */

$("#chuliDeployment").on('click',function(){
		var deploymentId = $('#renwuTable').DataTable().rows({selected:true}).data()[0].id;
		addVectorLayerToMapByDepId(deploymentId,'map');
});