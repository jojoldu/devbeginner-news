"use strict";

function readMore(offset) {
    $('.read-ready').show();

    $.ajax({
        url: '/api/v1/articles/' + offset,
        type: 'get',
        success: function (response) {
            $('.process-comm').hide();
            $('#posts-section').append(response.data);
        }
    });
}
