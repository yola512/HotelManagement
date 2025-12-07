// error pop-up
function showErrorPopup(message) {
    document.getElementById("errorMessage").textContent = message;
    let errorModal = new bootstrap.Modal(document.getElementById("errorModal"));
    errorModal.show();
}