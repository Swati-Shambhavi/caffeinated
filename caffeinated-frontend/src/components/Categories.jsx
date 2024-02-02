// Categories.jsx
import React, { useEffect } from 'react';
import CategoryItem from './CategoryItem';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCategories, deleteCategory, updateCategory } from '../store/slices/categorySlice';
import AddCategory from './AddCategory';
import { Link, useNavigate } from 'react-router-dom';

const Categories = () => {
  const { data, error, loading } = useSelector((state) => state.categories);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(fetchCategories());
  }, [dispatch]);

  const deleteHandler = (categoryId) => {
    dispatch(deleteCategory(categoryId)); 
  };

  const updateHandler = (categoryId, newCategoryName) => {
    dispatch(updateCategory({ categoryId, categoryName: newCategoryName }));
  };

  return (
    <>
    <div>
          <AddCategory/>
        </div>
      <div>{error && <div>
        <h2>Oops! {error}</h2>
        <button onClick={()=>dispatch(fetchCategories())}>Try again</button>
        </div>}</div>
     
      <div>{loading === 'loading' && <h3>Loading...</h3>}</div>
      <div>
        {data.map((category) => (
          <CategoryItem
            key={category.id}
            category={category}
            onDelete={deleteHandler}
            onUpdate={updateHandler}
          />
        ))
        }
      </div>
      <br/>
      <br/>
      <button onClick={()=>navigate(-1)}>Go Back</button>
      <button onClick={()=>navigate(+1)}>Forward</button>

    </>
  );
};

export default Categories;
