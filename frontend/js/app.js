// JavaScript to handle tab switching
const tabs = document.querySelectorAll('.tab');
const tabContents = document.querySelectorAll('.tab-content');

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        // Remove active class from existing active elements
        document.querySelector('.tab.active').classList.remove('active');
        document.querySelector('.tab-content.active').classList.remove('active');

        // Add active class to clicked tab and its corresponding content
        tab.classList.add('active');
        document.getElementById(tab.dataset.target).classList.add('active');
    });
});