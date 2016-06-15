$(function() {
	/* 优惠劵弹层 */
	var $overlay = $('#overlay');
	/**/function modalHidden($ele) {
		$ele.removeClass('modal-in');
		$ele.one('webkitTransitionEnd', function() {
			$ele.css({
				"display" : "none"
			});
			$overlay.removeClass('active');
		});
	}
	$('.coupon').click(function(e) {
		var coupon="bank238";
		// 获取数据
		$.ajax({
			type : "GET",
			url : getRootPath() + "/meet/coupon/activation",
			data : {
				'coupon' : coupon
			},
			success : function(data) {
				if (data == undefined) {
					alert("领取失败！");
					return;
				} else if (data.code == '2') {
					location.href = getRootPath() + "/meet/redirect/app?id=238";
					return;
				} else if (data.code == "0") {
					$overlay.addClass('active');
					$('.modal').css({
						"display" : "block"
					});
					$('.modal').animate(100, function() {
						$('.modal').addClass('modal-in');
					});
				}else{
					$overlay.addClass('active');
					$('.modal').css({
						"display" : "block"
					});
					$(".modal-hd").html("不能重复领取或现金券已发完！");
					$('.modal').animate(100, function() {
						$('.modal').addClass('modal-in');
					});
				}
			}
		});
	});
	/**/$('.btn-modal').click(function(e) {
		modalHidden($('.modal'));
		e.stopPropagation();
	});

	$overlay.click(function(e) {
		if (e.target.classList.contains('overlay')) {
			modalHidden($('.modal'));
		}
	});

});