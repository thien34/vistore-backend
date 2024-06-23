import { SearchOutlined } from '@ant-design/icons'
import { Button, Select } from 'antd'
import Input from 'antd/es/input/Input'
import { useState } from 'react'

interface CategorySearchProps {
    onSearch: (filter: { name: string; published: boolean | undefined }) => void
}

export default function CategorySearch({ onSearch }: Readonly<CategorySearchProps>) {
    const [name, setName] = useState<string>('')
    const [published, setPublished] = useState<boolean | undefined>(undefined)

    const handleSearch = () => {
        onSearch({ name, published })
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <div>
                <h3 className='text-xl font-bold'>Search</h3>
                <div className='flex pt-5 justify-between'>
                    <div className='flex gap-4'>
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
                            defaultValue={null}
                            value={published}
                            onChange={(value) => setPublished(value)}
                            options={[
                                { value: null, label: 'All' },
                                { value: true, label: 'Published only' },
                                { value: false, label: 'Unpublished only' },
                            ]}
                        />
                        <Button
                            size='large'
                            type='primary'
                            icon={<SearchOutlined />}
                            iconPosition={'end'}
                            onClick={handleSearch}
                        >
                            Search
                        </Button>
                    </div>
                    <div className=''>
                        <Button
                            // disabled={selectedRowKeys.length === 0}
                            // onClick={handleDelete}
                            type='primary'
                            className='me-5'
                            danger
                            size='large'
                        >
                            Delete
                        </Button>

                        <Button
                            className='bg-[#475569] text-white'
                            size='large'
                            // onClick={showModal}
                        >
                            Add
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    )
}
