jQuery.fn.numerico=function(user_options){
	var enabledKeys	=	[8,9,13,16, 17,18,19,20,27,32,33,34,35,36,37,38,39,40,44,45,46,110,190];
	this.each(
		function(index, object) {
			jQuery(object).keydown(
		
				function(e){
					if(enabledKeys.indexOf(e.which)>=0) return;					
					var s	=	this.value;
					var s	=	s.replace(/[^(\d)+((\.){,1}(\d+))?]/, "");
					if(isNaN(parseFloat(s))){
						this.value	=	0;
					}else{
						this.value	=	parseFloat(s);
					}
				}
			).keyup(
				function(e){
					
					if(enabledKeys.indexOf(e.which)>=0) return;					
					var s	=	this.value;
					var s	=	s.replace(/[^(\d)+((\.){,1}(\d+))?]/, "");
					if(isNaN(parseFloat(s))){
						this.value	=	0;
					}else{
						this.value	=	parseFloat(s);
					}
					
				}
			);
		}
	);
}