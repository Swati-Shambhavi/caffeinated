import React, { useEffect } from 'react';
import CategoryItem from './CategoryItem';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCategories, deleteCategory, updateCategory } from '../store/slices/categorySlice';
import AddCategory from './AddCategory';
import { useNavigate } from 'react-router-dom';

const AllCategories = () => {
  const { data, error, operationStatus } = useSelector((state) => state.categories);
  const dispatch = useDispatch();
  const navigate = useNavigate();
console.log("admin dashboard")
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
    <div className='mt-24 text-black'>
      <h1>here in admin dashboard</h1>
       <div>
          <AddCategory/>
        </div>
      <div>{operationStatus !== 'pending' && error &&  <div>
        <h2>Oops! {error}</h2>
        <button onClick={()=>dispatch(fetchCategories())}>Try again</button>
        </div>}</div>
     
      <div>{operationStatus === 'pending' && <h3>Loading...</h3>}</div>
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
</div>
  );
};

export default AllCategories;
