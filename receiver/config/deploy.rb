require 'bundler/capistrano'
set :application, "ressi"

set :scm, :git
set :repository,  "git://github.com/sizzlelab/ressi.git"
set :deploy_via, :remote_cache

if ENV['DEPLOY_ENV'] == "alpha" 
  set :deploy_to, "/var/datat/cos/ressi"
  set :server_name, "alpha"
  set :host, "alpha.sizl.org"
  set :user, "cos"
  set :branch, ENV['BRANCH'] || "master"
elsif ENV['DEPLOY_ENV'] == "alpha.aws"
  set :deploy_to, "/opt/ressi.alpha"
  set :server_name, "alpha"
  set :host, "46.137.99.187"
  set :user, "cos"
  set :branch, ENV['BRANCH'] || "master"
end


role :app, host
role :web, host
role :db, host, :primary => true

set :rails_env, :production
set :use_sudo, false

namespace :deploy do
  task :start do ; end
  task :stop do ; end
  task :restart, :roles => :app, :except => { :no_release => true } do
    run "#{try_sudo} touch #{File.join(current_path,'receiver/tmp','restart.txt')}"
  end
  
  task :setup do
    run "mkdir -p #{deploy_to}/releases"
    %w(log config).each do |dir|
      run "mkdir -p #{shared_path}/#{dir}"
    end
  end
  
  task :symlinks_to_shared_path do
    run "ln -nfs #{shared_path}/config/database.yml #{release_path}/receiver/config/database.yml"
    run "ln -nfs #{shared_path}/log #{release_path}/receiver/log"
    
  end
  
end

after "deploy:update_code" do
  deploy.symlinks_to_shared_path
end
