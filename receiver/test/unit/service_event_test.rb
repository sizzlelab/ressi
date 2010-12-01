require 'test_helper'

class ServiceEventTest < ActiveSupport::TestCase
  def test_should_create_service_event

    ce = ServiceEvent.create(
      :user_id => 'bU8aHSBEKr3AhYaaWPEYjL',
      :application_id => 'cWslSQyIyr3yiraaWPEYjL',
      :session_id => 'BAh7BzoPc2Vzc2lvbl9pZGkCVREiCmZsYXNoSUM6J0FjdGlvbkNvbnRyb2xs ZXI6OkZsYXNoOjpGbGFzaEhhc2h7AAY6CkB1c2VkewA=--e3ae3baef1b6f0e680af4107e0f086b5f59f0da6', 
      :ip_address => '130.233.194.26',
      :action => 'PeopleController#pending_friend_requests', 
      :parameters => '{"format": "json", "action": "pending_friend_requests", "controller": "people", "user_id": "bU8aHSBEKr3AhYaaWPEYjL"}', # JSON
      :return_value => '200',
      :http_user_agent => "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.5) Gecko/2008120121 Firefox/3.0.5", 
      :request_uri => "/people/bU8aHSBEKr3AhYaaWPEYjL/@pending_friend_requests", 
      :http_referer => "http://ossi.alpha.sizl.org/jsclient/v1/",
      :test_group_number => 3,
      :created_at => Time.now - 4.hours
    )

    assert ce.valid?
    assert ce.created_at < Time.now - 3.hours
  end
end
