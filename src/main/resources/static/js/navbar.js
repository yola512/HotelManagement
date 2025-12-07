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

    // checks if user scrolled right after the page is loaded to make sure navbar is displayed as planned
    handleNavbarScroll();

    // scroll listener
    window.addEventListener('scroll', handleNavbarScroll);
})