require 'test_helper'

class ServiceEventsControllerTest < ActionController::TestCase

  def test_create_with_erroneus_headers
    
    
    parameters =  '{"format": "json", "action": "pending_friend_requests", "controller": "people", "user_id": "bU8aHSBEKr3AhYaaWPEYjL", "message": {"body":"'
    10000.times { parameters << 'a'}
    parameters << '"}"}'
    
    timestamp = Time.now - 4.hours
    
    post :create, {:service_event => { :user_id => 'bU8aHSBEKr3AhYaaWPEYjL',
                    :application_id => 'cWslSQyIyr3yiraaWPEYjL',
                    :cos_session_id => 'BAh7BzoPc2Vzc2lvbl9pZGkCVREiCmZsYXNoSUM6J0FjdGlvbkNvbnRyb2xs ZXI6OkZsYXNoOjpGbGFzaEhhc2h7AAY6CkB1c2VkewA=--e3ae3baef1b6f0e680af4107e0f086b5f59f0da6', 
                    :ip_address => '130.233.194.26',
                    :action => 'PeopleController#pending_friend_requests', 
                    :parameters => parameters, # JSON
                    :return_value => '200',
                    :created_at => timestamp,
                    :headers => '{"SERVER_NAME": "ossi.alpha.sizl.org", "HTTP_MAX_FORWARDS": "10", "HTTP_X_PROTOTYPE_VERSION": "1.6.0.2", "PATH_INFO": "/people/bU8aHSBEKr'  #this is invalid JSON on purpose
                  }}
    
    assert_response :created
    assert_not_nil assigns["service_event"]
    assert_equal(assigns["service_event"].user_id, "bU8aHSBEKr3AhYaaWPEYjL")
    assert_equal(assigns["service_event"].http_user_agent, "unknown")
    assert_equal(timestamp, assigns["service_event"].created_at)
  end


end
