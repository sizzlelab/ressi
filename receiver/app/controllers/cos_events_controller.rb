require 'json'

class CosEventsController < ApplicationController
  before_filter :check_parameters_length
  
  def create
    # TODO: Check the incoming data for nil values etc.
    
    # Converting parameter 'headers' to Ruby-style associative array
    headers = JSON.parse(params[:cos_event]["headers"])
    
    @cos_event = CosEvent.new(:user_id => params[:cos_event]["user_id"],
                              :application_id => params[:cos_event]["application_id"],
                              :cos_session_id => params[:cos_event]["cos_session_id"],
                              :ip_address => params[:cos_event]["ip_address"],

                              :action => params[:cos_event]["action"],
                              :parameters => params[:cos_event]["parameters"],
                              :return_value => params[:cos_event]["return_value"],
                              :semantic_event_id => params[:cos_event]["semantic_event_id"],
                              :http_user_agent => headers["HTTP_USER_AGENT"],
                              :request_uri => headers["REQUEST_URI"],
                              :http_referer => headers["HTTP_REFERER"],
                              :created_at => params[:cos_event]["created_at"]
                            )

    @cos_event.save

    render :xml => @cos_event.to_xml, :status => :created, 
          :location => "http://localhost:9000/cos_event/#{@cos_event.id}"

  end
  
  private
  def check_parameters_length
    params[:cos_event]["parameters"] = "{}" if params[:cos_event]["parameters"].length > 10000
  end

end
