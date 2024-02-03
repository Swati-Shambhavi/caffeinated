// categoriesSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

// Async Thunk to fetch categories
export const fetchCategories = createAsyncThunk(
  'categories/fetchCategories',
  async () => {
    try {
      const response = await fetch(
        'http://localhost:8080/caffeinated/categories/api'
      );
      if (!response.ok) {
        let errorBody = await response.json();
        console.error(
          'Error response from server while fetching categories:',
          errorBody
        );
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      return data;
    } catch (error) {
      throw new Error('Error fetching categories. Please try again.');
    }
  }
);

// Async Thunk to add a new category
export const addCategory = createAsyncThunk(
  'categories/addCategory',
  async (category) => {
    const response = await fetch(
      'http://localhost:8080/caffeinated/categories/api',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(category),
      }
    );
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  }
);

export const updateCategory = createAsyncThunk(
  'categories/updateCategory',
  async ({ categoryId, categoryName }) => {
    const response = await fetch(
      `http://localhost:8080/caffeinated/categories/api/${categoryId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(categoryName),
      }
    );
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  }
);

// Async Thunk to delete a category
export const deleteCategory = createAsyncThunk(
  'categories/deleteCategory',
  async (categoryId) => {
    const response = await fetch(
      `http://localhost:8080/caffeinated/categories/api/${categoryId}`,

      {
        method: 'DELETE',
      }
    );
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  }
);

const initialState = {
  data: [],
  operationStatus: 'NA',
  selectedCategory: null,
  error: null,
};

const categorySlice = createSlice({
  name: 'categories',
  initialState,
  reducers: {
    setSelectedCategory: (state, action) => {
      state.selectedCategory = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCategories.pending, (state) => {
        state.operationStatus = 'pending';
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.operationStatus = 'success';
        state.data = action.payload.data;
        state.error = null;
      })
      .addCase(fetchCategories.rejected, (state, action) => {
        state.operationStatus = 'fail';
        state.error = action.error.message;
      })
      .addCase(addCategory.fulfilled, (state, action) => {
        state.data.push(action.payload.data);
      })
      .addCase(updateCategory.fulfilled, (state, action) => {
        const updatedCategoryId = action.meta.arg.categoryId;
        const updatedCategoryName = action.meta.arg.categoryName;

        const index = state.data.findIndex(
          (category) => category.id === updatedCategoryId
        );

        if (index !== -1) {
          state.data[index].name = updatedCategoryName;
        }
      })
      .addCase(deleteCategory.fulfilled, (state, action) => {
        // Modify this case to remove the category based on the categoryId
        const deletedCategoryId = action.meta.arg; // This gets the categoryId passed to the thunk
        state.data = state.data.filter(
          (category) => category.id !== deletedCategoryId
        );
      });
  },
});

export const { setSelectedCategory } = categorySlice.actions;
export default categorySlice.reducer;
