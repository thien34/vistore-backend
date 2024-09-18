import { Button, Table } from 'antd'
import useDiscountViewModel from './Discount.vm'
import DiscountSearch from './DiscountSearch'
import { PlusSquareOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'
import DiscountConfigs from './DiscountConfigs'

export default function DiscountManage() {
    const { columns, dataResources, filter, handleTableChange, handleSearch } = useDiscountViewModel()
    return (
        <div>
            <DiscountSearch search={handleSearch} />
            <div className='bg-[#fff] rounded-lg mt-4 shadow-md p-6 '>
                <Table
                    className='mt-6'
                    rowKey='id'
                    bordered
                    scroll={{ x: 400 }}
                    dataSource={dataResources?.items}
                    columns={columns}
                    pagination={{
                        current: filter.pageNo ?? 1,
                        pageSize: filter.pageSize ?? 6,
                        total: dataResources?.totalPages * (filter.pageSize ?? 6),
                        onChange: (page, pageSize) => handleTableChange({ current: page, pageSize: pageSize }),
                    }}
                />
                <Link to={ManagerPath.DISCOUNT_ADD}>
                    <Button type='primary' icon={<PlusSquareOutlined />}>
                        {DiscountConfigs.createTitle}
                    </Button>
                </Link>
            </div>
        </div>
    )
}
