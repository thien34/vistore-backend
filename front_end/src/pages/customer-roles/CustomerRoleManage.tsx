import { Button, Table } from 'antd'
import useCustomerRoleViewModel from './CustomerRole.vm'
import { Link } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'
import { PlusSquareOutlined } from '@ant-design/icons'
import CustomerRoleConfigs from './CustomerRoleConfigs'

export default function CustomerRoleManage() {
    const { columns, handleTableChange, filter, listResponse, isLoading } = useCustomerRoleViewModel()

    return (
        <>
            {isLoading && <p>Loading ...</p>}
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                {listResponse && (
                    <Table
                        rowKey='id'
                        bordered
                        columns={columns}
                        dataSource={listResponse.items}
                        scroll={{ x: 650 }}
                        pagination={{
                            current: filter.pageNo ?? 1,
                            pageSize: filter.pageSize ?? 6,
                            total: listResponse.totalPages * (filter.pageSize ?? 6),
                            onChange: (page, pageSize) => handleTableChange({ current: page, pageSize }),
                        }}
                    />
                )}
                <Link to={ManagerPath.CUSTOMER_ROLE_ADD}>
                    <Button size='large' type='primary' icon={<PlusSquareOutlined />}>
                        {CustomerRoleConfigs.createTitle}
                    </Button>
                </Link>
            </div>
        </>
    )
}
