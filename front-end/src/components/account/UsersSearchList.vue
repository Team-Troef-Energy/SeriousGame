<template>
  <div class="user-search-container">
    <div class="search-wrapper">
      <input 
        v-model="searchQuery" 
        type="text" 
        placeholder="Search by email..." 
        class="search-input"
        aria-label="Search users by email"
      />
    </div>

    <ul class="user-list">
      <li v-for="user in filteredUsers" :key="user.id || user.email" class="user-item">
        <div class="user-info">
          <div class="user-details">
            <p class="user-email"><strong>Email:</strong> {{ user.email || 'N/A' }}</p>
            <p class="user-role"><strong>Role:</strong> <span :class="user.role === 'admin' ? 'role-admin' : 'role-user'">
                {{ user.role }}
              </span>
            </p>
          </div>
          <button 
            class="admin-btn" 
            @click="promoteToAdmin(user)" 
            :disabled="user.role === 'admin'"
            :title="user.role === 'admin' ? 'Already admin' : 'Promote to admin'"
            :aria-label="`Promote ${user.email} to admin`"
          >
            <svg width="100%" height="100%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path
                d="M17 8.99994C17 8.48812 16.8047 7.9763 16.4142 7.58579C16.0237 7.19526 15.5118 7 15 7M15 15C18.3137 15 21 12.3137 21 9C21 5.68629 18.3137 3 15 3C11.6863 3 9 5.68629 9 9C9 9.27368 9.01832 9.54308 9.05381 9.80704C9.11218 10.2412 9.14136 10.4583 9.12172 10.5956C9.10125 10.7387 9.0752 10.8157 9.00469 10.9419C8.937 11.063 8.81771 11.1823 8.57913 11.4209L3.46863 16.5314C3.29568 16.7043 3.2092 16.7908 3.14736 16.8917C3.09253 16.9812 3.05213 17.0787 3.02763 17.1808C3 17.2959 3 17.4182 3 17.6627V19.4C3 19.9601 3 20.2401 3.10899 20.454C3.20487 20.6422 3.35785 20.7951 3.54601 20.891C3.75992 21 4.03995 21 4.6 21H7V19H9V17H11L12.5791 15.4209C12.8177 15.1823 12.937 15.063 13.0581 14.9953C13.1843 14.9248 13.2613 14.8987 13.4044 14.8783C13.5417 14.8586 13.7588 14.8878 14.193 14.9462C14.4569 14.9817 14.7263 15 15 15Z"
                stroke="currentColor" 
                stroke-width="2" 
                stroke-linecap="round" 
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>
      </li>
      <li v-if="!filteredUsers.length" class="no-results">
        Geen gebruikers gevonden met dat email.
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { databaseService } from '../../services/firebase/DatabaseService';

const users = ref<any[]>([]);
const searchQuery = ref('');

const promoteToAdmin = async (user: any) => {
  try {
    if (user.role === 'admin') {
      window.alert(`De gebruiker met email ${user.email} is al een admin.`);
      return;
    }

    await databaseService.updateUserRole(user.email, 'admin');
    user.role = 'admin';
    window.alert(`${user.email} is nu een admin!`);
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

  return filtered.slice(0, 20);
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
  max-width: 1200px;
  margin: 2rem auto;
  padding: 1rem;
  width: 100%;
  box-sizing: border-box;
}

.search-wrapper {
  max-width: 600px;
  margin: 0 auto 2rem;
}

.search-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s ease;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.search-input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.user-list {
  list-style: none;
  padding: 0;
  display: grid;
  gap: 1.5rem;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
}

.user-item {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 1.5rem;
  background-color: #ffffff;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
}

.user-item:hover {
  transform: translateY(-2px);
}

.user-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1.5rem;
}

.user-details {
  flex: 1;
}

.user-email,
.user-role {
  margin: 0.5rem 0;
  font-size: 1rem;
  line-height: 1.5;
}

.user-email strong,
.user-role strong {
  color: #1f2937;
  font-weight: 600;
}

.role-admin {
  color: #16a34a;
  font-weight: 500;
}

.role-user {
  color: #6b7280;
  font-weight: 500;
}

.admin-btn {
  background-color: #f3f4f6;
  border: 1px solid #d1d5db;
  padding: 0.5rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

.admin-btn:hover:not(:disabled) {
  background-color: #e5e7eb;
  transform: scale(1.05);
}

.admin-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.admin-btn svg {
  width: 24px;
  height: 24px;
  stroke: #4b5563;
}

.no-results {
  grid-column: 1 / -1;
  text-align: center;
  padding: 2rem;
  color: #6b7280;
  font-style: italic;
}

@media (max-width: 768px) {
  .user-search-container {
    padding: 1rem;
  }

  .user-list {
    grid-template-columns: 1fr;
  }

  .user-item {
    padding: 1rem;
  }

  .user-info {
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  .admin-btn {
    width: 36px;
    height: 36px;
  }
}

@media (max-width: 480px) {
  .search-input {
    font-size: 0.9rem;
    padding: 0.5rem;
  }

  .user-email,
  .user-role {
    font-size: 0.9rem;
  }

  .admin-btn {
    width: 32px;
    height: 32px;
  }

  .admin-btn svg {
    width: 20px;
    height: 20px;
  }
}
</style>