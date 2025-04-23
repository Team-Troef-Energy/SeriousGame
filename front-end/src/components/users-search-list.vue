<template>
    <div class="user-search-container">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Search by email..."
        class="search-input"
      />
  
      <ul class="user-list">
        <li
          v-for="user in filteredUsers"
          :key="user.id || user.email"
          class="user-item"
        >
          <p><strong>Email:</strong> {{ user.email || 'N/A' }}</p>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, computed, onMounted } from 'vue';
  import { databaseService } from '../services/firebase/DatabaseService.ts'; 
  
  const users = ref<any[]>([]);
  const searchQuery = ref('');
  
  const filteredUsers = computed(() => {
  const filtered = !searchQuery.value
    ? users.value
    : users.value.filter(user =>
        user.email?.toLowerCase().includes(searchQuery.value.toLowerCase())
      );

  return filtered.slice(0, 5);
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
  </style>  