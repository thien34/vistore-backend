import AppActions from "@/constants/AppActions "
import { SearchOutlined } from "@ant-design/icons"
import { Button, Select } from "antd"
import Input from 'antd/es/input/Input'
import { useState } from "react"
import { Link } from "react-router-dom"

//Declare Type Filter
export  type Filter = {
    name: string 
    published: boolean | undefined
}
// Declare ManufactureSearchProperties
interface ManufactureSearchProperties {
    search: (filter: Filter) => void
    handleDelete: () => void
    selectedRows: React.Key[]
}
//Declare Component ManufactureSearch
export default function ManufactureSearch({ search, handleDelete, selectedRows }: Readonly<ManufactureSearchProperties>) {
    
    const [Manufacturename, setManufactureName] = useState<string>('')

    const [published, setPublished] = useState<boolean | undefined>(undefined)

    const handleSearchManufacture = () => {
        const filter: Filter = {
            name: Manufacturename,
            published: published
        };
        // Call search with filter
        search(filter)
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
                            placeholder='Manufacture name'
                            value = {Manufacturename}
                            onChange={(e) => setManufactureName(e.target.value)}
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
                            onClick={handleSearchManufacture}
                        >
                            {AppActions.SEARCH}
                        </Button>
                    </div>
                    <div className=''>
                        <Button
                            disabled={selectedRows.length === 0}
                            onClick={handleDelete}
                            type='primary'
                            className='me-5'
                            danger
                            size='large'
                        >
                            {AppActions.DELETE}
                        </Button>
                        <Link to='/admin/manufacturers/add'>
                            <Button className='bg-[#475569] text-white' size='large'>
                                {AppActions.ADD}
                            </Button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}