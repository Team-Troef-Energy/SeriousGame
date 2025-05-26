class PythonService{
    fetchMessage = async (message: { [key: string]: any }) => {
        const aiBackendUrl = {
            "localhost": 'http://localhost:5000',
            "dev.troefgame.jonaqhan.nl": "https://dev.troefgame.jonaqhan.nl/ai-backend",
            "troefgame.duckdns.org": "http://troefgame.duckdns.org/ai-backend",
        }[window.location.hostname] || null; // Hostname does not include port

        try {
          const response = await fetch(`${aiBackendUrl}/python-data`, {
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