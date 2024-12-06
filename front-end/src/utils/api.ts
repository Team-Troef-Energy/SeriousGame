export async function fetchStartLevel(levelNumber: string): Promise<any> {
  try {
    const response = await fetch('http://localhost:8080/levels/start/' + levelNumber);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error);
    throw error;
  }
}

// add update level