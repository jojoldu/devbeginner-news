$(window).on("load", function() {
    "use strict";
    $('.undo-btn').on("click", function(){
        window.history.back();
    });
});