<template>
    <div class="dashboard-page container">
      <!-- Tab Navigation -->
      <div class="tabs">
        <button
          class="tab"
          :class="{ 'tab-active': activeTab === 'levelEditor' }"
          @click="activeTab = 'levelEditor'"
        >
          Level Editor
        </button>
        <button
          class="tab"
          :class="{ 'tab-active': activeTab === 'users' }"
          @click="activeTab = 'users'"
        >
          Users
        </button>
      </div>
  
      <!-- Tab Content -->
      <div class="tab-content">
        <LevelEditor v-if="activeTab === 'levelEditor'" />
        <UsersSearchList v-if="activeTab === 'users'" />
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref } from 'vue';
  import LevelEditor from '../../components/account/LevelEditor.vue';
  import UsersSearchList from '../../components/account/UsersSearchList.vue';
  
  export default defineComponent({
    name: 'DashboardPage',
    components: {
      LevelEditor,
      UsersSearchList,
    },
    setup() {
      const activeTab = ref('levelEditor'); // Default to LevelEditor tab
      return { activeTab };
    },
  });
  </script>
  
  <style scoped>
  .dashboard-page {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    height: 90vh;
    padding: 2rem 1rem;
    box-sizing: border-box;
  }
  
  .tabs {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 2rem;
    border-bottom: 2px solid #e5e7eb;
    width: 100%;
    max-width: 1200px;
    justify-content: center;
  }
  
  .tab {
    padding: 0.75rem 1.5rem;
    font-size: 1rem;
    font-weight: 500;
    color: #4b5563;
    background: none;
    border: none;
    border-bottom: 2px solid transparent;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .tab:hover {
    color: #1f2937;
    border-bottom: 2px solid #3b82f6;
  }
  
  .tab-active {
    color: #1f2937;
    border-bottom: 2px solid #3b82f6;
    font-weight: 600;
  }
  
  .tab-content {
    width: 100%;
    max-width: 1200px;
    flex: 1;
    overflow-y: auto;
  }
  
  /* Responsive adjustments */
  @media (max-width: 768px) {
    .dashboard-page {
      padding: 1rem;
    }
  
    .tabs {
      gap: 0.25rem;
    }
  
    .tab {
      padding: 0.5rem 1rem;
      font-size: 0.9rem;
    }
  }
  
  @media (max-width: 480px) {
    .tab {
      padding: 0.5rem 0.75rem;
      font-size: 0.85rem;
    }
  }
  </style>