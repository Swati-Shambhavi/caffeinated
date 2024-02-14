import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import {setSelectedCategory} from '../store/slices/categorySlice';
import AuthorizedElement from '../utility/AuthorizedElement';

const CategoryItem = ({ category, onDelete, onUpdate }) => {
  const [newCategoryName, setNewCategoryName] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleDelete = () => {
    onDelete(category.id);
  };

  const handleUpdate = () => {
    onUpdate(category.id, newCategoryName);
    setNewCategoryName(''); 
  };
  const handleViewProducts = () => {
    dispatch(setSelectedCategory(category));
    navigate(`/categories/${category.id}/products`);
  }

  return (
    <div>
      <h3> Id: {category.id} - {category.name}</h3>
      <input
        type="text"
        value={newCategoryName}
        onChange={(e) => setNewCategoryName(e.target.value)}
        placeholder="New Category Name"
      />
       <AuthorizedElement roles={["ADMIN"]}><button onClick={handleUpdate}>Update</button></AuthorizedElement> 
      <AuthorizedElement roles={["ADMIN"]}><button onClick={handleDelete}>Delete</button></AuthorizedElement> 
      <button onClick={handleViewProducts}>View Products</button>
    </div>
  );
};

export default CategoryItem;