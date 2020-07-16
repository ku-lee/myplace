var main = {
    init : function () {
        var _this = this;
        $('#btn-login').on('click', function () {
            _this.login();
        });

        $('#btn-join').on('click', function () {
            _this.join();
        });

        $('#btn-search').on('click', function () {
            _this.search();
        });

        $('#btn-previous').on('click', function () {
            _this.previous();
        });

        $('#btn-next').on('click', function () {
            _this.next();
        });
    },
    login : function () {
        var data = {
            memberId: $('#memberId').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/v1/api/member/login',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('로그인 되었습니다');
            window.location.href = '/search';
        }).fail(function (error) {
            alert(error.responseJSON.message);
        });
    },
    join : function () {
        var data = {
            memberId: $('#memberId').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/v1/api/member/create',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('가입이 완료 되었습니다');
            window.location.href = '/';
        }).fail(function (error) {
            alert(error.responseJSON.message);
        });
    },
    search : function () {
        window.location.href = '/result?keyword='+$('#keyword').val()+'&increase=true';
    },
    previous : function () {
        var keyword = $('#keyword').val();
        var currentPage = $('#currentPage').val();
        if(currentPage == 1) {
            alert('첫 페이지 입니다');
            return;
        }
        currentPage --;

        window.location.href = '/result?keyword='+keyword+'&page='+currentPage;
    },
    next : function () {
        var keyword = $('#keyword').val();
        var currentPage = $('#currentPage').val();
        var pageableCount = $('#pageableCount').val();
        if(pageableCount == currentPage){
            alert('마지막 페이지 입니다');
            return;
        }
        currentPage ++;

        window.location.href = '/result?keyword='+keyword+'&page='+currentPage;
    }

};

main.init();