export async function fetchStartLevel(levelNumber: string): Promise<any> {
  try {
    const response = await fetch(
        `http://localhost:8080/levels/start/${levelNumber}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        }
    );
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("There was a problem with the fetch operation:", error);
    throw error;
  }
}

export async function fetchUpdateLevel(
  levelNumber: string,
  levelUpdateDto: any
): Promise<any> {
  try {
    const response = await fetch(
      "http://localhost:8080/levels/update/" + levelNumber,
      {
        method: "POST",
        body: JSON.stringify(levelUpdateDto),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      }
    );
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("There was a problem with the fetch operation:", error);
    throw error;
  }
}
