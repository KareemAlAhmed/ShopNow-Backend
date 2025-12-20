function confirmDelete() {
      if (confirm('Are you sure you want to delete this user? This action cannot be undone.')) {
            document.querySelector('.deleteForm').submit();
       }
 }


