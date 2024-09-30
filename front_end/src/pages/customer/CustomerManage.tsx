import ManagerPath from '@/constants/ManagerPath'
import { Button, Table } from 'antd'
import { Link } from 'react-router-dom'
import { PlusSquareOutlined } from '@ant-design/icons'
import useCustomerViewModel from './Customer.vm'
import CustomerConfigs from './CustomerConfigs'
import CustomerSearch from './CustomerSearch'

export default function CustomerManage() {
    const { columns, dataResources, filter, handleTableChange, isLoading, handleSearch } = useCustomerViewModel()

    return (
        <>
            <CustomerSearch search={handleSearch} />
            <div className='bg-[#fff] rounded-lg mt-4 shadow-md p-6 '>
                {isLoading && <p>Loading ...</p>}
                {dataResources && (
                    <Table
                        rowKey='id'
                        bordered
                        columns={columns}
                        dataSource={dataResources.items}
                        scroll={{ x: 650 }}
                        pagination={{
                            current: filter.pageNo ?? 1,
                            pageSize: filter.pageSize ?? 6,
                            total: dataResources.totalPages * (filter.pageSize ?? 6),
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize }),
                        }}
                    />
                )}
                <Link to={ManagerPath.CUSTOMER_ADD}>
                    <Button
                        className='bg-[#374151] border-[#374151] text-white'
                        size='large'
                        icon={<PlusSquareOutlined />}
                    >
                        {CustomerConfigs.createTitle}
                    </Button>
                </Link>
            </div>
        </>
    )
}
