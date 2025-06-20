$(document).ready(function () {
    loadAllComplaintData();
    bindEventHandlers();
});


function bindEventHandlers() {
    // Insert (default submit)
    $('#complaintForm').on('submit', handleInsert);

    // Row click to populate form
    $('table tbody').on('click', 'tr', handleRowSelection);

    // Update button
    $('.btn-update').on('click', handleUpdate);

    // Delete button
    $('.btn-delete').on('click', handleDelete);

    // Clear button
    $('.btn-clear').on('click', clearForm);
}

/* ------------------ Handlers ------------------ */


function handleInsert(e) {
    if ($('#formAction').val() === 'insert') {
        if (!validateForm()) {
            e.preventDefault();
        }
    }
}


function handleRowSelection() {
    const cells = $(this).children('td');

    $('#complaint_id').val(cells.eq(0).text());
    $('#title').val(cells.eq(1).text());
    $('#description').val(cells.eq(2).text());

    $(this).addClass('selected-row').siblings().removeClass('selected-row');
}

function handleUpdate() {
    if (!validateForm()) return;
    setFormActionAndSubmit('update');
}


function handleDelete() {
    if (!$('#complaint_id').val()) {
        showAlert('error', 'Please select a complaint to delete.');
        return;
    }

    Swal.fire({
        title: 'Are you sure?',
        text: 'This complaint will be permanently deleted!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            setFormActionAndSubmit('delete');
        }
    });
}

function setFormActionAndSubmit(action) {
    $('#formAction').val(action);
    $('#complaintForm').submit();
}


function loadAllComplaintData() {
    const $tbody = $(".complaints-table tbody");
    $tbody.empty();

    if (!complaintList || complaintList.length === 0) {
        $tbody.append(`<tr><td colspan="6">No complaints found.</td></tr>`);
        return;
    }

    complaintList.forEach(c => {
        const row = `
            <tr>
                <td>${c.complaintId}</td>
                <td>${c.title}</td>
                <td>${c.description}</td>
                <td>${c.status}</td>
                <td>${c.remarks}</td>
                <td>${c.createdAt}</td>
            </tr>`;
        $tbody.append(row);
    });
}


function clearForm() {
    $('#complaint_id').val('');
    $('#title').val('');
    $('#description').val('');
    $('.selected-row').removeClass('selected-row');
}


function showAlert(icon, message) {
    Swal.fire({
        icon: icon,
        title: icon === 'error' ? 'Error' : 'Info',
        text: message,
        confirmButtonColor: '#3085d6'
    });
}

function validateForm() {
    const title = $('#title').val().trim();
    const desc = $('#description').val().trim();

    if (!title || !desc) {
        showAlert('error', 'Title and Description are required!');
        return false;
    }
    return true;
}
