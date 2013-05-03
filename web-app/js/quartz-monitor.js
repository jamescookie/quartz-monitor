if (jQuery) {
	(function($, window) {
		var
        xOffset = 10,
		yOffset = 20,
        init = function() {
            $('.quartz-to-hide').hide();
            $('body').data('reschedulePopupOpen', false);
			$('.quartz-tooltip')
                .hover(function(e){
                    var tooltipData = $(this).data('tooltip');
                    if (tooltipData === "") return;
                    displayToolTip(tooltipData, (e.pageX + yOffset), (e.pageY - xOffset));
                },
                function(){
                    $("#quartz-tooltip").remove();
                })
                .mousemove(function(e){
                    $("#quartz-tooltip")
                        .css("top",(e.pageY - xOffset) + "px")
                        .css("left",(e.pageX + yOffset) + "px");
                });
            $('.quartz-countdown').each(function() {
                var item = $(this),
                    remaining = item.data('next-run');
                if (remaining === "") return;
                countdown(item, remaining);
            });
           displayClock($('#clock'));
           if (jQuery.ui) {
               $(".reschedule").click(function(e) {
                   e.preventDefault();
                   $(this).next().dialog({
                       modal: true,
                       title: "Reschedule Trigger",
                       buttons: {
                           "Reschedule": function() {
                               $(this).find('form').submit();
                           },
                           "Cancel": function() {
                               $(this).dialog("close");
                           }
                       },
                       open: function(event,ui) {
                           $('body').data('reschedulePopupOpen', true);
                       },
                       close: function(event,ui) {
                           $('body').data('reschedulePopupOpen', false);
                           location.reload(true);
                       }
                   });
               });
           }
		},

        displayClock = function(item) {
            if (item.clock !== undefined) {
                item.clock({"timestamp":new Date(item.data('time'))});
            }
        },

        reloadPage = function() {
            var original = $(this).css('color');
            var delay = 200;
            $(this).animate({
                backgroundColor: '#f00',
                color: '#fff'
            }, delay, function() {
                $(this).animate({
                    backgroundColor: 'transparent',
                    color: original
                }, delay, function() {
                    $(this).animate({
                        backgroundColor: '#f00',
                        color: '#fff'
                    }, delay, function() {
                        $(this).animate({
                            backgroundColor: 'transparent',
                            color: original
                        }, delay, function() {
                             location.reload(true);
                        });
                    });
                });
            });
        },

        countdown = function(item, remaining) {
            if (item.countdown !== undefined) {
                item.countdown(
                   {until: new Date(remaining),
                       onExpiry: function() {
                           if ($("body").data("reschedulePopupOpen") == false) {
                               reloadPage.call($(this));
                           }
                       },
                       compact: true
                   });
            }
        },

        displayToolTip = function(tooltipData, x, y) {
            $('<p></p>')
                    .text(tooltipData)
                    .attr('id', 'quartz-tooltip')
                    .css("top", y + "px")
                    .css("left", x + "px")
                    .appendTo('body')
                    .fadeIn("fast");
        };

		$(init);

	}(jQuery, this));
}
