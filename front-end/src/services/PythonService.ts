class PythonService{
    fetchMessage = async (message: { [key: string]: any }) => {
        try {
          const response = await fetch("http://localhost:5000/python-data", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(message)
          });
      
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
}

export const pythonService = new PythonService();