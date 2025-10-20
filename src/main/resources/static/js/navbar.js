// navbar
document.addEventListener('DOMContentLoaded', function() {
    const navbar = document.querySelector('.navbar');

    const handleNavbarScroll = () => {
        if (window.scrollY > 5) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    };

    // sprawdza od razu po załadowaniu (jeśli np. odświeżysz w połowie strony)
    handleNavbarScroll();

    // nasłuchuje scrolla
    window.addEventListener('scroll', handleNavbarScroll);
})

// navbar stops being transparent after scroll
// window.addEventListener('scroll', function() {
//     const navbar = document.querySelector('.navbar');
//     if (window.scrollY > 50) {
//         navbar.classList.add('scrolled');
//     }
//     else {
//         navbar.classList.remove('scrolled');
//     }
// });