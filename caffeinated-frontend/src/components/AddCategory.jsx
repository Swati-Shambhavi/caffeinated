// YourComponent.jsx
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addCategory } from '../store/slices/categorySlice';

const YourComponent = () => {
  const dispatch = useDispatch();
  const [categoryName, setCategoryName] = useState('');

  const handleAddCategory = () => {
    dispatch(addCategory({ categoryName }));
    setCategoryName('');
  };

  return (
    <div>
      <h2>Add a New Category</h2>
      <div>
        <label htmlFor="categoryName">Category Name:</label>
        <input
          type="text"
          id="categoryName"
          value={categoryName}
          onChange={(e) => setCategoryName(e.target.value)}
        />
      </div>
      <button onClick={handleAddCategory}>Add Category</button>
    </div>
  );
};

export default YourComponent;
