<template>
  <div class="user-search-container">
    <input v-model="searchQuery" type="text" placeholder="Search by email..." class="search-input" />

    <ul class="user-list">
      <li v-for="user in filteredUsers" :key="user.id || user.email" class="user-item">
        <p><strong>Email:</strong> {{ user.email || 'N/A' }}</p>
        <p><strong>Rol:</strong> {{ user.role }}</p>
        <button class="admin-btn" @click="promoteToAdmin(user)" title="Promoveer tot admin">
          <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path
              d="M17 8.99994C17 8.48812 16.8047 7.9763 16.4142 7.58579C16.0237 7.19526 15.5118 7 15 7M15 15C18.3137 15 21 12.3137 21 9C21 5.68629 18.3137 3 15 3C11.6863 3 9 5.68629 9 9C9 9.27368 9.01832 9.54308 9.05381 9.80704C9.11218 10.2412 9.14136 10.4583 9.12172 10.5956C9.10125 10.7387 9.0752 10.8157 9.00469 10.9419C8.937 11.063 8.81771 11.1823 8.57913 11.4209L3.46863 16.5314C3.29568 16.7043 3.2092 16.7908 3.14736 16.8917C3.09253 16.9812 3.05213 17.0787 3.02763 17.1808C3 17.2959 3 17.4182 3 17.6627V19.4C3 19.9601 3 20.2401 3.10899 20.454C3.20487 20.6422 3.35785 20.7951 3.54601 20.891C3.75992 21 4.03995 21 4.6 21H7V19H9V17H11L12.5791 15.4209C12.8177 15.1823 12.937 15.063 13.0581 14.9953C13.1843 14.9248 13.2613 14.8987 13.4044 14.8783C13.5417 14.8586 13.7588 14.8878 14.193 14.9462C14.4569 14.9817 14.7263 15 15 15Z"
              stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
          </svg>
        </button>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { databaseService } from '../services/firebase/DatabaseService.ts';

const users = ref<any[]>([]);
const searchQuery = ref('');

const promoteToAdmin = async (user: any) => {
  try {
    await databaseService.updateUserRole(user.email, 'admin');
    user.role = 'admin';
    window.alert(user.email + ' is nu een admin!');
  } catch (error) {
    console.error(`Failed to promote user ${user.email}:`, error);
  }
};


const filteredUsers = computed(() => {
  const filtered = !searchQuery.value
    ? users.value
    : users.value.filter(user =>
      user.email?.toLowerCase().includes(searchQuery.value.toLowerCase())
    );

  return filtered.slice(0, 3);
});


onMounted(async () => {
  try {
    users.value = await databaseService.getAllUsers();
  } catch (error) {
    console.error('Error fetching users:', error);
  }
});
</script>

<style scoped>
.user-search-container {
  max-width: 600px;
  margin: 2rem auto;
  padding: 1rem;
}

.search-input {
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.user-list {
  list-style: none;
  padding: 0;
}

.user-item {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 1rem;
  margin-bottom: 1rem;
  background-color: #fafafa;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.admin-btn {
  background-color: #fff;
  border: rgba(0, 0, 0, .1) solid 1px;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
}

.admin-btn:hover {
  background-color: #f4f4f4;
  cursor: pointer;
}

.admin-btn svg {
  width: 16px;
  height: 16px;
}
</style>