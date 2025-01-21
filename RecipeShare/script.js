const apiKey = '3f1280e04b8549f4a01869828373e145';
const recipeList = document.getElementById('recipe-list');
const savedRecipes = JSON.parse(localStorage.getItem('savedRecipes')) || [];
const welcomeMessage = document.getElementById('welcome-message');
const logoutButton = document.getElementById('logout');
const modal = document.getElementById('recipe-modal');
const closeModal = document.getElementById('close-modal');
const modalRecipeDetail = document.getElementById('modal-recipe-detail');
const saveRecipeButton = document.getElementById('save-recipe');
let allRecipes = []; // Store all fetched recipes

// Fetch recipes from Spoonacular API
async function fetchRecipes() {
    const response = await fetch(`https://api.spoonacular.com/recipes/random?apiKey=${apiKey}&number=10`);
    const data = await response.json();
    allRecipes = data.recipes; // Store all recipes for searching
    displayRecipes(allRecipes); // Display all recipes initially
}

// Display recipes on the home page
function displayRecipes(recipes) {
    recipeList.innerHTML = '';
    recipes.forEach(recipe => {
        const card = document.createElement('div');
        card.className = 'recipe-card';
        card.innerHTML = `
            <h3>${recipe.title}</h3>
            <img src="${recipe.image}" alt="${recipe.title}">
            <button onclick="viewRecipe(${recipe.id})">View More</button>
            <button onclick="saveRecipe(${recipe.id})">Save Recipe</button>
        `;
        recipeList.appendChild(card);
    });
}

// View recipe details in a modal
async function viewRecipe(id) {
    const response = await fetch(`https://api.spoonacular.com/recipes/${id}/information?apiKey=${apiKey}`);
    const recipe = await response.json();
    modalRecipeDetail.innerHTML = `
        <h2>${recipe.title}</h2>
        <img src="${recipe.image}" alt="${recipe.title}">
        <h3>Ingredients</h3>
        <ul>${recipe.extendedIngredients.map(ingredient => `<li>${ingredient.original}</li>`).join('')}</ul>
        <h3>Instructions</h3>
        <p>${recipe.instructions}</p>
    `;
    modal.style.display = "block";
}

// Save recipe to local storage
function saveRecipe(id) {
    if (!savedRecipes.includes(id)) {
        savedRecipes.push(id);
        localStorage.setItem('savedRecipes', JSON.stringify(savedRecipes));
        alert('Recipe saved!');
    } else {
        alert('Recipe already saved!');
    }
}

// Close modal
closeModal.onclick = function() {
    modal.style.display = "none";
}

// Load saved recipes on profile page
function loadSavedRecipes() {
    const savedList = document.getElementById('saved-recipes');
    savedList.innerHTML = '';
    savedRecipes.forEach(id => {
        fetch(`https://api.spoonacular.com/recipes/${id}/information?apiKey=${apiKey}`)
            .then(response => response.json())
            .then(recipe => {
                const card = document.createElement('div');
                card.className = 'recipe-card';
                card.innerHTML = `
                    <h3>${recipe.title}</h3>
                    <img src="${recipe.image}" alt="${recipe.title}">
                `;
                savedList.appendChild(card);
            });
    });
}

// Handle user registration
document.getElementById('register-form')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    localStorage.setItem('user', JSON.stringify({ name, email, password }));
    alert('Registration successful! You can now log in.');
    window.location.href = 'login.html'; // Redirect to login page
});

// Handle user login
document.getElementById('login-form')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;
    const user = JSON.parse(localStorage .getItem('user'));
    if (user && user.email === email && user.password === password) {
        alert('Login successful!');
        welcomeMessage.textContent = `Welcome, ${user.name}!`;
        localStorage.setItem('loggedIn', true);
        window.location.href = 'profile.html';
    } else {
        alert('Invalid email or password.');
    }
});

// Logout functionality
if (logoutButton) {
    logoutButton.addEventListener('click', function() {
        localStorage.removeItem('loggedIn');
        window.location.href = 'index.html';
    });
}

// Initialize app
if (document.getElementById('recipe-list')) {
    fetchRecipes();
}
if (document.getElementById('saved-recipes')) {
    loadSavedRecipes();
}
if (welcomeMessage) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
        welcomeMessage.textContent = `Welcome, ${user.name}!`;
    }
}

// Search functionality
document.getElementById('search-button')?.addEventListener('click', function() {
    const query = document.getElementById('search').value.toLowerCase();
    const filteredRecipes = allRecipes.filter(recipe => 
        recipe.title.toLowerCase().includes(query) || 
        recipe.extendedIngredients.some(ingredient => ingredient.name.toLowerCase().includes(query))
    );
    displayRecipes(filteredRecipes);
});

document.getElementById('search')?.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        const query = this.value.toLowerCase();
        const filteredRecipes = allRecipes.filter(recipe => 
            recipe.title.toLowerCase().includes(query) || 
            recipe.extendedIngredients.some(ingredient => ingredient.name.toLowerCase().includes(query))
        );
        displayRecipes(filteredRecipes);
    }
});

// Close modal when clicking outside of it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};