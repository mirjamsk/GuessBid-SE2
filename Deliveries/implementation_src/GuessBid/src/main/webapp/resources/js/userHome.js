/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('select>option:first').html('Category');
});


startCountdown();
function destroyCountdown() {
    $('.time-remaining').countdown('destroy' ? 'destroy' : {until: 0});

}
function startCountdown() {

    $('.time-remaining').each(function (index) {
        var txt = $(this).text();
        if (/.*sec.*/.test(txt)) {
            var minutes = 0;
            var sec = 0;
            if (/.*min.*/.test(txt)) {
                var temp = txt.split("min");
                minutes = parseInt(temp[0]);
                sec = parseInt(temp[1].split("sec")[0]);
            } else {
                var temp = txt.split("sec");
                sec = parseInt(temp[0]);
            }
            var now = new Date();
            $(this).countdown({
                until: new Date(now.getTime() + (minutes * 60 + sec) * 1000),
                format: 'M S'
            });
        }
    });
}
;