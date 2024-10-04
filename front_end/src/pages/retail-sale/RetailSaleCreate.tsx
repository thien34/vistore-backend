import ManagerPath from '@/constants/ManagerPath'
import { Button, Input, Tabs } from 'antd'
import { Link } from 'react-router-dom'
import useRetailSaleCreateViewModal from './RetailSaleCreate.vm'

export default function RetailSaleCreate() {
    const { activeKey, items, onChange, onEdit } = useRetailSaleCreateViewModal()
    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <div>
                    <div className='flex justify-between'>
                        <div className='flex gap-4 flex-wrap'>
                            <Input
                                className='w-56'
                                size='large'
                                placeholder='Find product'
                                // value={name}
                                // onChange={(e) => setName(e.target.value)}
                            />
                        </div>
                        <div className='flex gap-4 flex-wrap'>
                            <Link to={ManagerPath.CATEGORY_ADD}>
                                <Button className='default-btn-color' size='large'>
                                    QR
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                <Tabs type='editable-card' onChange={onChange} activeKey={activeKey} onEdit={onEdit} items={items} />
            </div>
        </>
    )
}
