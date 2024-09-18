import React from 'react'
import { Button } from 'antd'
import Search from 'antd/es/input/Search'
import AppActions from '@/constants/AppActions'

interface ProductTagSearchProps {
    onSearch: (term: string) => void
    handleDelete: () => void
    selectedRowKeys: React.Key[]
}

const ProductTagSearchComponent: React.FC<ProductTagSearchProps> = ({ onSearch, handleDelete, selectedRowKeys }) => {
    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <div>
                <h3 className='text-xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-between'>
                    <div className='flex gap-4 flex-wrap'>
                        <Search size='large' placeholder='Product tag name' onSearch={onSearch} />
                    </div>
                    <div className=''>
                        <Button
                            disabled={selectedRowKeys.length === 0}
                            onClick={handleDelete}
                            type='primary'
                            className='me-5'
                            danger
                            size='large'
                        >
                            {AppActions.DELETE}
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export const ProductTagSearch = React.memo(ProductTagSearchComponent)
