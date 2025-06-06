import { ref } from 'vue';
import { textModal } from '../../../types/global/modals/TextModal';

export function useTextModal() {
  const isTextModalVisible = ref(false);
  const textModalContent = ref<textModal>({
    header: 'Alert',
    body: 'Nothing to show'
  });

  const showModal = (header: string, body: string) => {
    textModalContent.value.header = header;
    textModalContent.value.body = body;
    isTextModalVisible.value = true;
  };

  return {
    isTextModalVisible,
    textModalContent,
    showModal
  };
}