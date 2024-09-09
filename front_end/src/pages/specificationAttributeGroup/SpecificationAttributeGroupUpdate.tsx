import { Button, Col, Form, Input, InputNumber, Row, Table } from 'antd'
import useSpecificationAttributeGroupUpdateViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupUpdate.vm.ts'
import { SaveOutlined } from '@ant-design/icons'
import SpecificationAttributeGroupConfigs from './SpecificationAttributeGroupConfigs'

function SpecificationAttributeGroupUpdate() {
    const {
        form,
        onFinish,
        handleDelete,
        layout,
        handleUpdate,
        unGroupColumns,
        modalData,
        rowSelection,
        handleModalDelete,
        selectedModalRowKeys,
    } = useSpecificationAttributeGroupUpdateViewModel()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>{SpecificationAttributeGroupConfigs.updateTitle}</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='default-btn-color' size='large' icon={<SaveOutlined />} onClick={handleUpdate}>
                        {SpecificationAttributeGroupConfigs.btnSave}
                    </Button>
                    <Button type='primary' size='large' danger onClick={handleDelete}>
                        {SpecificationAttributeGroupConfigs.btnDelete}
                    </Button>
                </div>
            </div>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <Form
                    form={form}
                    {...layout}
                    layout='vertical'
                    name='edit_specification'
                    onFinish={onFinish}
                    size='large'
                    initialValues={{ displayOrder: 0 }}
                >
                    <Row>
                        <Col span={12}>
                            <Form.Item
                                name='name'
                                label='Name'
                                rules={[
                                    { required: true, message: 'Please input the group name!' },
                                    { max: 100, message: 'Name cannot exceed 100 characters!' },
                                ]}
                            >
                                <Input />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label='Display order'
                                name='displayOrder'
                                rules={[
                                    { required: true, message: 'Please enter the display order!' },
                                    {
                                        type: 'number',
                                        min: 0,
                                        max: 2000000,
                                        message: 'Display order must be between 0 and 2,000,000!',
                                    },
                                ]}
                            >
                                <InputNumber className='w-36' type='number' />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
                <div className='flex justify-between mb-5'>
                    <div className='text-base'>
                        <div className=''>{SpecificationAttributeGroupConfigs.updateTitle}</div>
                    </div>
                    <div className='flex gap-4 flex-wrap'>
                        <Button
                            key='delete'
                            type='primary'
                            danger
                            onClick={handleModalDelete}
                            disabled={selectedModalRowKeys.length === 0}
                        >
                            {SpecificationAttributeGroupConfigs.btnDelete}
                        </Button>
                    </div>
                </div>
                <Table
                    rowKey='id'
                    bordered
                    pagination={false}
                    columns={unGroupColumns}
                    dataSource={modalData}
                    rowSelection={rowSelection}
                />
            </div>
        </>
    )
}

export default SpecificationAttributeGroupUpdate
