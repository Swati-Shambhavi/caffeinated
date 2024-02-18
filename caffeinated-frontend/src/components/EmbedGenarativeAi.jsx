import axios from 'axios'
import React, { useState } from 'react'

const EmbedGenarativeAi = () => {
  const key = ''
  const [searchText, setSearchText] = useState('')
  const [genAiResponse, setGenAiResponse] = useState({
    isSearchOffensive: false,
    unsafeText: '',
    safeText: '',
  })
  const requestData = {
    contents: [
      {
        parts: [
          {
            text: 'Write a story about a magic backpack.',
          },
        ],
      },
    ],
  }
  const handleSearchEvent = async (event) => {
    const responseStructureDetail =
      ' Prepare the response in 2 sections. first section should be list of ingredients seperated by -. second secion should be list of steps to prepare the recipe, seperated by -. please adhere to the asked response structure'
    event.preventDefault()
    console.log(searchText)
    requestData.contents[0].parts[0].text =
      'How to make' + searchText + responseStructureDetail
    console.log(requestData)
    let response = null
    try {
      response = await axios.post(
        'https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=' +
          key,
        requestData,
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      )
      console.log('response=', response)
      const data = await response.data
      if (
        'promptFeedback' in data &&
        data.promptFeedback.hasOwnProperty('blockReason')
      ) {
        console.log('Unsafe response = ', data.promptFeedback)
        setGenAiResponse({
          isSearchOffensive: true,
          unsafeText:
            'The search has been blocked due to ' +
            data.promptFeedback.blockReason +
            ' reasons.',
          safeText: '',
        })
      } else {
        console.log('Safe Response = ', data.candidates[0].content.parts[0])
        setGenAiResponse({
          isSearchOffensive: false,
          safeText: data.candidates[0].content.parts[0].text,
        })
      }
    } catch (e) {
      console.log('eror', e)
    }
  }
  const clearHandler = () => {
    setGenAiResponse({ isSearchOffensive: false, safeText: '', unsafeText: '' })
    setSearchText('')
  }
  return (
    <div className='p-8 border-t border-b border-black'>
      <div className='m-4 flex align-baseline justify-center'>
        <h3 className='pt-2'>How to make</h3>
        <input
          type='text'
          name='serachGenAi'
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          className='border-black border-b-2 ml-2 p-1 bg-transparent'
        />
        <button
          className='bg-black text-amber-50 ml-2 p-2 rounded-sm'
          onClick={handleSearchEvent}
        >
          Search
        </button>
        <button
          className='bg-black text-amber-50 ml-2 p-2 rounded-sm'
          onClick={clearHandler}
        >
          Clear
        </button>
      </div>
      <div className='flex justify-center align-middle'>
        {genAiResponse.isSearchOffensive ? (
          <p className='text-red-600 font-bold'>{genAiResponse.unsafeText}</p>
        ) : (
          <p>{genAiResponse.safeText}</p>
        )}
      </div>
    </div>
  )
}

export default EmbedGenarativeAi
