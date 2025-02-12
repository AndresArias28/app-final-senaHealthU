const checkToken = (request, response, next) => {
    const authHeader = request.headers["authorization"];
  
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      return response
        .status(401)
        .json({ message: "Unauthorized: No Bearer token provided" });
    }
  
    const token = authHeader.split(" ")[1];
  
    if (!token) {
      return response
        .status(401)
        .json({ message: "Unauthorized: Token is missing" });
    }
  
    // validaciones
  
    next();
  };
  
  module.exports = checkToken;