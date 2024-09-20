import AppActions from '@/constants/AppActions'
import ManagerPath from '@/constants/ManagerPath'
import { SearchOutlined } from '@ant-design/icons'
import { Button, Select } from 'antd'
import Input from 'antd/es/input/Input'
import { useState } from 'react'
import { Link } from 'react-router-dom'

interface CategorySearchProps {
    onSearch: (filter: { name: string; published: boolean | undefined }) => void
    handleDelete: () => void
    selectedRowKeys: React.Key[]
}

export default function CategorySearch({ onSearch, selectedRowKeys, handleDelete }: Readonly<CategorySearchProps>) {
    const [name, setName] = useState<string>('')
    const [published, setPublished] = useState<string | undefined>(undefined)

    const handleSearch = () => {
        onSearch({ name, published: published === 'true' ? true : published === 'false' ? false : undefined })
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <div>
                <h3 className='text-xl font-bold'>{AppActions.SEARCH}</h3>
                <div className='flex px-5 pt-5 justify-between'>
                    <div className='flex gap-4 flex-wrap'>
                        <Input
                            className='w-56'
                            size='large'
                            placeholder='Category name'
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <Select
                            className='w-56'
                            size='large'
                            defaultValue={'all'}
                            value={published}
                            onChange={(value) => setPublished(value)}
                            options={[
                                { value: 'all', label: 'All' },
                                { value: 'true', label: 'Published only' },
                                { value: 'false', label: 'Unpublished only' },
                            ]}
                        />
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
                    <div className='flex gap-4 flex-wrap'>
                        <Button
                            disabled={selectedRowKeys.length === 0}
                            onClick={handleDelete}
                            type='primary'
                            danger
                            size='large'
                        >
                            {AppActions.DELETE}
                        </Button>
                        <Link to={ManagerPath.CATEGORY_ADD}>
                            <Button className='default-btn-color' size='large'>
                                {AppActions.ADD}
                            </Button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
