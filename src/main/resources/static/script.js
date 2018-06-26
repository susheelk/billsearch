$(document).ready(function () {
    var box = $('#query-box');
    box.on('keypress', function (e) {
        if(e.which == 13) {

            $.get("/"+box.val(), {}, function (text) {
                box.val("");
                console.log(text);
                $("#code").text(JSON.stringify(text.data, undefined, 2));
            });
            box.val("");
        }
    });
});