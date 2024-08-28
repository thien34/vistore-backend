import AppActions from '@/constants/AppActions '
import { useState } from 'react'
import { ProductSelect } from './ProductNameSelect'
import { Button } from 'antd'
import { SearchOutlined } from '@ant-design/icons'
interface StockQuantityHistorySearchProps {
    onSearch: (filter: { productId: string }) => void
}

const productData = [
    { id: 1, name: 'Product 1' },
    { id: 2, name: 'Product 2' },
    { id: 3, name: 'Product 3' },
    { id: 4, name: 'Product 4' },
]
export default function StockQuantityHistorySearch({ onSearch }: Readonly<StockQuantityHistorySearchProps>) {
    const [productId, setProductId] = useState<string>('')
    const handleSearch = () => {
        onSearch({ productId })
    }
    const handleChangeSelectProduct = (value: number | null) => {
        setProductId(value ? value.toString() : '')
    }
    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <div>
                <h3 className='text-xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-between'>
                    <div className='flex gap-4 flex-wrap'>
                        <ProductSelect data={productData} onChange={handleChangeSelectProduct} />
                        <Button
                            size='large'
                            type='primary'
                            icon={<SearchOutlined />}
                            iconPosition={'end'}
                            onClick={handleSearch}
                        >
                            {AppActions.SEARCH}
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    )
}
