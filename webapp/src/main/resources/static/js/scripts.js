const fabButton = document.getElementById('fabButton');
const fabOptions = document.getElementById('fabOptions');

fabButton.addEventListener('mouseenter', function () {
    fabOptions.classList.add('show');
});

fabButton.addEventListener('mouseleave', function () {
    setTimeout(function () {
        if (!fabOptions.matches(':hover')) {
            fabOptions.classList.remove('show');
        }
    }, 100);
});

fabOptions.addEventListener('mouseleave', function () {
    setTimeout(function () {
        if (!fabButton.matches(':hover')) {
            fabOptions.classList.remove('show');
        }
    }, 100);
});

fabOptions.addEventListener('mouseenter', function () {
    fabOptions.classList.add('show');
});