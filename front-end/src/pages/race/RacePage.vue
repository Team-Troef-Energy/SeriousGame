<template>
    <div class="race-page container">
        <div class="header">
            <button class="btn-back" @click="navigateTo('/race')">Ga terug</button>
        </div>
        <div class="content">
            <div class="name">
                <input v-model="raceName" type="text" readonly />
                <button class="btn-edit-name" @click="createRaceNameChangeModal"
                    @race-name-change="handleRaceNameChange"><svg width="50%" height="50%" viewBox="0 0 24 24"
                        fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M18 10L14 6M2.49997 21.5L5.88434 21.124C6.29783 21.078 6.50457 21.055 6.69782 20.9925C6.86926 20.937 7.03242 20.8586 7.18286 20.7594C7.35242 20.6475 7.49951 20.5005 7.7937 20.2063L21 7C22.1046 5.89543 22.1046 4.10457 21 3C19.8954 1.89543 18.1046 1.89543 17 3L3.7937 16.2063C3.49952 16.5005 3.35242 16.6475 3.24061 16.8171C3.1414 16.9676 3.06298 17.1307 3.00748 17.3022C2.94493 17.4954 2.92195 17.7021 2.87601 18.1156L2.49997 21.5Z"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg></button>
            </div>
            <div class="navigation">
                <button class="btn-level-editor" @click="navigateTo(`/race/${raceId}/level-editor`)">
                    <p>Level
                        editor</p>
                    <svg width="25%" height="25%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M12 20.4722C13.0615 21.4223 14.4633 22 16 22C19.3137 22 22 19.3137 22 16C22 13.2331 20.1271 10.9036 17.5798 10.2102M6.42018 10.2102C3.87293 10.9036 2 13.2331 2 16C2 19.3137 4.68629 22 8 22C11.3137 22 14 19.3137 14 16C14 15.2195 13.851 14.4738 13.5798 13.7898M18 8C18 11.3137 15.3137 14 12 14C8.68629 14 6 11.3137 6 8C6 4.68629 8.68629 2 12 2C15.3137 2 18 4.68629 18 8Z"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </button>
                <button class="btn-level-editor" @click="navigateTo(`/race/${raceId}/levels`)">
                    <p>Level overzicht</p>
                    <svg width="25%" height="25%" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M17.5 17H6.5M17.5 13H6.5M3 9H21M7.8 3H16.2C17.8802 3 18.7202 3 19.362 3.32698C19.9265 3.6146 20.3854 4.07354 20.673 4.63803C21 5.27976 21 6.11984 21 7.8V16.2C21 17.8802 21 18.7202 20.673 19.362C20.3854 19.9265 19.9265 20.3854 19.362 20.673C18.7202 21 17.8802 21 16.2 21H7.8C6.11984 21 5.27976 21 4.63803 20.673C4.07354 20.3854 3.6146 19.9265 3.32698 19.362C3 18.7202 3 17.8802 3 16.2V7.8C3 6.11984 3 5.27976 3.32698 4.63803C3.6146 4.07354 4.07354 3.6146 4.63803 3.32698C5.27976 3 6.11984 3 7.8 3Z"
                            stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                </button>
            </div>
            <div class="delete">
                <button class="btn-delete-race" @click="createDeleteRaceModal">Verwijder race</button>
            </div>
        </div>
        <Teleport to="body">
            <RaceNameChangeModal :show="isRaceNameChangeModalVisible" @close="isRaceNameChangeModalVisible = false"
                @race-name-change="handleRaceNameChange" />
        </Teleport>
        <Teleport to="body">
            <RaceDeleteModal :show="isRaceDeleteModalVisible" @close="isRaceDeleteModalVisible = false"
                @race-delete="handleRaceDelete" />
        </Teleport>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRoute } from 'vue-router';
import router from '../../router/Router';
import RaceNameChangeModal from '../../components/race/RaceNameChangeModal.vue';
import RaceDeleteModal from '../../components/race/RaceDeleteModal.vue';

export default defineComponent({
    name: 'RacePage',
    components: { RaceNameChangeModal, RaceDeleteModal },
    setup() {
        let isRaceNameChangeModalVisible = ref(false)
        let isRaceDeleteModalVisible = ref(false)

        const createRaceNameChangeModal = async () => {
            isRaceNameChangeModalVisible.value = true;
        };

        const createDeleteRaceModal = async () => {
            isRaceDeleteModalVisible.value = true;
        };

        const route = useRoute();
        let raceId = route.params.id;
        let raceName = ref<string>('test');

        if (raceId != 'f47ac10b-58cc-4372-a567-0e02b2c3d479') {
            router.push('/');
        }

        const navigateTo = (location: string) => {
            router.push(location);
        };

        const handleRaceNameChange = async (newRaceName: string) => {
            raceName.value = newRaceName;
            isRaceNameChangeModalVisible.value = false;
        }

        const handleRaceDelete = async () => {
            isRaceDeleteModalVisible.value = false;
            navigateTo('/race')
        }

        return {
            isRaceNameChangeModalVisible,
            isRaceDeleteModalVisible,
            createRaceNameChangeModal,
            createDeleteRaceModal,
            raceId,
            raceName,
            navigateTo,
            handleRaceNameChange,
            handleRaceDelete
        };
    }
});
</script>

<style scoped>
.race-page {
    display: flex;
    flex-direction: column;
    height: 90vh;
}

.header {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    height: 5rem;
    flex: 1;
}

.content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 2rem 0rem 2rem 0rem;
    flex: 10;
}

.navigation {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    gap: 1rem;
}

.navigation button {
    height: 10rem;
}

button {
    width: 8rem;
    height: 3rem;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    border: solid 1px rgba(0, 0, 0, .1);
    background-color: #fff;
    color: #000;
    cursor: pointer;
}

.btn-back {
    margin-right: 5%;
}

button:hover {
    cursor: pointer;
    background-color: #f8f8f8;
}

.name {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 5rem;
    gap: 1rem;
}

input {
    height: 3rem;
    border-radius: 0.5rem;
    border: solid 1px rgba(0, 0, 0, .1);
    text-align: center;
    width: 16rem;
}

.btn-edit-name {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 3rem;
    height: 3rem;
    padding: 0rem;
}

.btn-delete-race {
    width: 10rem;
    white-space: nowrap;
}

@media (min-width: 475px) {
    .navigation {
        gap: 5rem;
    }

    .navigation button {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 12rem;
        width: 12rem;
        gap: 1rem;
    }

    .navigation button p {
        white-space: nowrap;
    }
}
</style>