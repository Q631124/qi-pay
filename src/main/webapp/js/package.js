$(document).ready(function () {
    setBtn();
});

function setBtn() {
    $('.model').on('click', 'a.buy', function () {
        if ($('input:checked').length < 1) {
            alert("请选择");
        } else {
            window.location.href = '/page/confirm.do?classItem=banana&price=1';
        }
    });

    $('.fruit').on('change', 'input[name=fruit]', function () {
        if ($(this).prop('checked')) {
            $(this).parent().addClass('checked');
            $('.fruit input[name=fruit]').not('input:checked').parent().removeClass('checked');
            sumPrice();
        }
    });

    $('input[name=fruit]:checked').each(function () {
        $(this).parent().addClass('checked');
    });

    sumPrice();
}


function sumPrice() {
    var price = 0;
    if ($('input[name=fruit]:checked').length > 0) {
        price = $('input[name=fruit]:checked').parent().find('.price span').text();
    }
    $('.sum-price .price').text('￥' + price);
}