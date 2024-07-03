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

document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.delete-category-btn');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const categoryId = this.dataset.categoryId;
            openDeleteDialog(categoryId);
        });
    });
});

function openDeleteDialog(categoryId) {
    const dialog = document.createElement('div');
    dialog.classList.add('dialog');

    dialog.innerHTML = `
        <div class="dialog-content">
            <p>Do you want to delete this category with all expenses or without expenses?</p>
            <button onclick="deleteCategory(${categoryId}, true)">Delete with Expenses</button>
            <button onclick="deleteCategory(${categoryId}, false)">Delete without Expenses</button>
            <button onclick="closeDialog()">Cancel</button>
        </div>
    `;

    document.body.appendChild(dialog);
}

function deleteCategory(categoryId, withExpenses) {
    // Make a DELETE request to your server
    const url = `/category/delete/${categoryId}?withExpenses=${withExpenses}`;

    fetch(url, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                alert('Category deleted successfully');
                // Optionally, reload the page or update the UI
            } else {
                alert('Error deleting category');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

    closeDialog();
}

function closeDialog() {
    const dialog = document.querySelector('.dialog');
    if (dialog) {
        dialog.remove();
    }
}