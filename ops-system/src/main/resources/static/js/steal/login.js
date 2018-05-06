function login() {
    $('.login').addClass('test');
    setTimeout(function () {
        $('.login').addClass('testtwo');
    }, 300);
    setTimeout(function () {//认证中--出来
        $('.authent').show().animate({right: -320}, {
            easing: 'easeOutQuint',
            duration: 600,
            queue: false
        });
        $('.authent').animate({opacity: 1}, {
            duration: 200,
            queue: false
        }).addClass('visible');
    }, 500);
    $.ajax({
        url: "/common/login",
        type: "POST",
        dataType: "json",
        async: false,
        data: {username: $("#username").val(), password: $("#password").val()},
        success: function (data) {
            setTimeout(function () {//认证中--消失
                $('.authent').show().animate({right: 90}, {
                    easing: 'easeOutQuint',
                    duration: 600,
                    queue: false
                });
                $('.authent').animate({opacity: 0}, {
                    duration: 200,
                    queue: false
                }).addClass('visible');
                $('.login').removeClass('testtwo');
                setTimeout(function () {
                    $('.login').removeClass('test');
                    $('.login div').fadeOut(123);
                    if (data.code == 0) {
                        setTimeout(function () {
                            $('.success').fadeIn();
                        }, 200);
                        setTimeout(function () {
                            window.location = "/common/login";///登陆成功后跳转链接
                        }, 1000);
                    }
                    else {
                        setTimeout(function () {
                            $('.fail').fadeIn();
                            $("#tips").show();
                        }, 200);
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                }, 300);
            }, 1400);
        }
    });
};
$('input[type="text"],input[type="password"]').focus(function () {
    $(this).prev().animate({'opacity': '1'}, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({'opacity': '.5'}, 200);
});
$('input[type="text"],input[type="password"]').keyup(function () {
    if (!$(this).val() == '') {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});
var open = 0;
$('.tab').click(function () {
    $(this).fadeOut(200, function () {
        $(this).parent().animate({'left': '0'});
    });
});