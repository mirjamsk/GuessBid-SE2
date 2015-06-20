/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateBid($bidInput) {
    $('.ui-messages-error').fadeOut('fast');
    var bid = $bidInput.val().trim();

    if ((bid !== '') && (!(/^\d+(\.\d{1,2})?$/.test(bid)) || bid === "0")) {
        $bidInput.val('');
        $("#bid-js-messages").fadeIn().text('Please enter a positive number with up to 2 decimal places');
        return false;
    }
    else
        $("#bid-js-messages").text('').fadeOut();

}

(function () {
    var txt = $('.time-remaining').text();
    
    if (/.*sec.*/.test(txt)) {
        var minutes = 0;
        var sec = 0;
        if (/.*min.*/.test(txt)) {
            var temp = txt.split("min");
            minutes = parseInt(temp[0]);
            sec = parseInt(temp[1].split("sec")[0]);
        }else {
            var temp = txt.split("sec");
            sec = parseInt(temp[0]);
        }

        var now = new Date();
        $('.time-remaining').countdown({
            until: new Date(now.getTime() + (minutes * 60 + sec) * 1000),
            format: 'M S',
            onExpiry: hideBidForm
        });
    }
    function hideBidForm() {
        $('.bid-wrap').fadeOut();
    }
})();
