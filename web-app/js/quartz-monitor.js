if (jQuery) {
	(function($, window) {
		var
        xOffset = 10,
		yOffset = 20,
        init = function() {
            $('.quartz-to-hide').hide();
			$('.quartz-tooltip')
                .hover(function(e){
                    var tooltipData = $(this).data('tooltip');
                    if (tooltipData === "") return;
                    $('<p></p>')
                            .text(tooltipData)
                            .attr('id', 'quartz-tooltip')
                            .css("top", (e.pageY - xOffset) + "px")
                            .css("left", (e.pageX + yOffset) + "px")
                            .appendTo('body')
                            .fadeIn("fast");
                },
                function(){
                    $("#quartz-tooltip").remove();
                })
                .mousemove(function(e){
                    $("#quartz-tooltip")
                        .css("top",(e.pageY - xOffset) + "px")
                        .css("left",(e.pageX + yOffset) + "px");
                });
		};

		$(init);

	}(jQuery, this));
}
