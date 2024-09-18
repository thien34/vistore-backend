import { Form, Input, Button, Checkbox, DatePicker, InputNumber, Select, Card } from 'antd'
import 'tailwindcss/tailwind.css'
import useDiscountCreateViewModel from './DiscountCreate.vm'
import { DiscountTypeEnum } from './DiscountTypeEnum'
import { DiscountLimitationEnum } from './DiscountLimitationEnum'
const { Option } = Select
export default function DiscountCreate() {
    const {
        form,
        usePercentage,
        requiresCouponCode,
        discountLimitation,
        discountType,
        handleUsePercentageChange,
        handleRequiresCouponCodeChange,
        handleDiscountLimitationChange,
        handleDiscountTypeChange,
        handleSubmit,
        discountLoading,
    } = useDiscountCreateViewModel()

    return (
        <div className='p-6 bg-white rounded-md shadow-md'>
            <Form form={form} layout='vertical' onFinish={handleSubmit} className='space-y-4'>
                <Card title='Basic Information' className='mb-4'>
                    <Form.Item name='isActive' valuePropName='checked' initialValue={false}>
                        <Checkbox>Is active</Checkbox>
                    </Form.Item>

                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[{ required: true, message: 'Please input the discount name!' }]}
                    >
                        <Input size='large' />
                    </Form.Item>
                </Card>

                <Card title='Discount Type and Details' className='mb-4'>
                    <Form.Item name='discountType' label='Discount type' initialValue={DiscountTypeEnum.ORDER_TOTAL}>
                        <Select size='large' onChange={handleDiscountTypeChange} value={discountType}>
                            <Option value={DiscountTypeEnum.ORDER_TOTAL}>Assigned to order total</Option>
                            <Option value={DiscountTypeEnum.PRODUCTS}>Assigned to products</Option>
                            <Option value={DiscountTypeEnum.CATEGORIES}>Assigned to categories</Option>
                            <Option value={DiscountTypeEnum.MANUFACTURERS}>Assigned to manufacturers</Option>
                            <Option value={DiscountTypeEnum.ORDER_SUBTOTAL}>Assigned to order subtotal</Option>
                        </Select>
                    </Form.Item>

                    {discountType === DiscountTypeEnum.CATEGORIES && (
                        <Form.Item name='appliedToSubCategories' valuePropName='checked'>
                            <Checkbox>Applied to subcategories</Checkbox>
                        </Form.Item>
                    )}

                    {(discountType === DiscountTypeEnum.PRODUCTS ||
                        discountType === DiscountTypeEnum.CATEGORIES ||
                        discountType === DiscountTypeEnum.MANUFACTURERS) && (
                        <Form.Item
                            name='maxDiscountedQuantity'
                            label='Maximum discounted quantity'
                            rules={[{ required: true, message: 'Please input the maximum discounted quantity!' }]}
                        >
                            <InputNumber size='large' />
                        </Form.Item>
                    )}
                </Card>

                <Card title='Discount Values' className='mb-4'>
                    <Form.Item name='usePercentage' valuePropName='checked' initialValue={false}>
                        <Checkbox onChange={handleUsePercentageChange} checked={usePercentage}>
                            Use percentage
                        </Checkbox>
                    </Form.Item>

                    {usePercentage ? (
                        <Card className='p-4 mt-4' title='Percentage Details'>
                            <Form.Item
                                name='discountPercentage'
                                label='Discount percentage'
                                rules={[{ required: true, message: 'Please input the discount percentage!' }]}
                            >
                                <InputNumber size='large' />
                            </Form.Item>

                            <Form.Item
                                name='maxDiscountAmount'
                                label='Maximum discount amount'
                                rules={[{ required: true, message: 'Please input the maximum discount amount!' }]}
                            >
                                <InputNumber size='large' addonAfter='USD' />
                            </Form.Item>
                        </Card>
                    ) : (
                        <Card className='p-4 mt-4' title='Amount Details'>
                            <Form.Item
                                name='discountAmount'
                                label='Discount amount'
                                rules={[{ required: true, message: 'Please input the discount amount!' }]}
                            >
                                <InputNumber size='large' addonAfter='USD' />
                            </Form.Item>
                        </Card>
                    )}
                </Card>

                <Card title='Coupon and Dates' className='mb-4'>
                    <Form.Item name='requiresCouponCode' valuePropName='checked' initialValue={false}>
                        <Checkbox onChange={handleRequiresCouponCodeChange} checked={requiresCouponCode}>
                            Requires coupon code
                        </Checkbox>
                    </Form.Item>

                    {requiresCouponCode && (
                        <Form.Item name='couponCode' label='Coupon code'>
                            <Input size='large' placeholder='Enter coupon code' />
                        </Form.Item>
                    )}

                    <div className='flex space-x-4'>
                        <Form.Item
                            name='startDate'
                            label='Start date'
                            rules={[{ required: true, message: 'Please select the start date!' }]}
                            className='flex-1'
                        >
                            <DatePicker size='large' showTime className='w-full' />
                        </Form.Item>

                        <Form.Item
                            name='endDate'
                            label='End date'
                            rules={[{ required: true, message: 'Please select the end date!' }]}
                            className='flex-1'
                        >
                            <DatePicker size='large' showTime className='w-full' />
                        </Form.Item>
                    </div>
                </Card>

                <Card title='Additional Information' className='mb-4'>
                    <Form.Item name='isCumulative' valuePropName='checked' initialValue={false}>
                        <Checkbox>Cumulative with other discounts</Checkbox>
                    </Form.Item>

                    <Form.Item
                        name='discountLimitationId'
                        label='Discount limitation'
                        initialValue={DiscountLimitationEnum.UNLIMITED}
                    >
                        <Select size='large' onChange={handleDiscountLimitationChange} value={discountLimitation}>
                            <Option value={DiscountLimitationEnum.UNLIMITED}>Unlimited</Option>
                            <Option value={DiscountLimitationEnum.N_TIMES_ONLY}>N times only</Option>
                            <Option value={DiscountLimitationEnum.N_TIMES_PER_CUSTOMER}>N times per customer</Option>
                        </Select>
                    </Form.Item>

                    {(discountLimitation === DiscountLimitationEnum.N_TIMES_ONLY ||
                        discountLimitation === DiscountLimitationEnum.N_TIMES_PER_CUSTOMER) && (
                        <Form.Item
                            initialValue={1}
                            name='limitationTimes'
                            label='N times'
                            rules={[{ required: true, message: 'Please input the number of times!' }]}
                        >
                            <InputNumber min={1} />
                        </Form.Item>
                    )}

                    <Form.Item
                        name='minOderAmount'
                        label='Minimum Order Amount'
                        rules={[{ required: true, message: 'Please input the minimum order amount!' }]}
                    >
                        <InputNumber size='large' addonAfter='USD' />
                    </Form.Item>

                    <Form.Item name='comment' label='Admin comment'>
                        <Input.TextArea size='large' placeholder='Add comments here...' />
                    </Form.Item>
                </Card>

                <Form.Item>
                    <Button size='large' type='primary' htmlType='submit' loading={discountLoading}>
                        Save
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}
